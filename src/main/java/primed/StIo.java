package primed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import primed.shapes.Param;
import primed.shapes.Shape;
import primed.shapes.Shapes;

final class StIo extends BaseAppState {

	private static final Logger logger = LoggerFactory.getLogger(StIo.class);

	@Override
	protected void initialize(Application app) {
	}

	@Override
	protected void cleanup(Application app) {
	}

	@Override
	protected void onEnable() {
	}

	@Override
	protected void onDisable() {
	}

	public void toJson(Node scene, File out) {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode root = mapper.createArrayNode();

		for (Spatial s : scene.getChildren()) {
			if (!(s instanceof Geometry)) {
				logger.debug("export, skipping = {}", s);
				continue;
			}

			ObjectNode o = mapper.createObjectNode();
			o.put("name", s.getName());
			JsonNode translation = mapper.valueToTree(s.getLocalTranslation().toArray(null));
			o.set("translation", translation);
			JsonNode rotation = mapper.valueToTree(s.getLocalRotation().toAngles(null));
			o.set("rotation", rotation);
			JsonNode scale = mapper.valueToTree(s.getLocalScale().toArray(null));
			o.set("scale", scale);

			ObjectNode sh = o.objectNode();
			sh.put("name", s.getControl(CtShapeRef.class).shape().getClass().getName());

			ObjectNode params = sh.objectNode();
			sh.set("params", params);

			for (Param<?> param : s.getControl(CtShapeRef.class).params()) {
				params.put(param.name(), param.valueAsString());
			}

			o.set("shape", sh);

			root.add(o);
		}

		logger.debug("json = {}", root);
		try {
			mapper.writeValue(out, root);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void fromJson(File file) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(file);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		Shapes shapes = new Shapes();

		for (JsonNode o : root) {
			String shapeClassname = o.path("shape").path("name").textValue();
			Shape shape = shapes.forClass(shapeClassname);

			List<Param<?>> params = new ArrayList<>();

			o.path("shape").path("params").fieldNames().forEachRemaining(paramName -> {
				String paramValue = o.path("shape").path("params").get(paramName).textValue();

				Param<?> param = shape.find(paramName);
				params.add(param.newParam(paramValue));
			});

			shape.bind(params);

			Geometry geometry = getState(StScene.class).geometry(shape);

			Vector3f translation = new Vector3f(o.path("translation").path(0).floatValue(), o.path("translation").path(1)
					.floatValue(), o.path("translation").path(2).floatValue());
			geometry.setLocalTranslation(translation);

			Quaternion rotation = new Quaternion().fromAngles(
					o.path("rotation").path(0).floatValue(),
					o.path("rotation").path(1).floatValue(),
					o.path("rotation").path(2).floatValue());
			geometry.setLocalRotation(rotation);

			Vector3f scale = new Vector3f(o.path("scale").get(0).floatValue(), o.path("scale").get(1).floatValue(), o.path(
					"scale").get(2).floatValue());
			geometry.setLocalScale(scale);
		}
	}

	public void toJ3o(Node scene, File file) {
		Node clone = scene.clone(true);

		clone.depthFirstTraversal(child -> {
			if (child.getControl(CtShapeRef.class) != null)
				child.removeControl(CtShapeRef.class);

			if (child instanceof Geometry) {
				Material material = ((Geometry) child).getMaterial();

				Material m = new Material(material.getMaterialDef());
				material.getParams().forEach(param -> {
					m.setParam(param.getName(), param.getVarType(), param.getValue());
				});

				((Geometry) child).setMaterial(m);
			}
		});

		try {
			BinaryExporter.getInstance().save(clone, file);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
