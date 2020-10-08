package primed;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.MouseInput;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.event.DefaultMouseListener;
import com.simsilica.lemur.event.MouseEventControl;

import common.material.MtlLighting;
import primed.shapes.Shape;

final class StScene extends BaseAppState {

	private static final Logger logger = LoggerFactory.getLogger(StScene.class);

	private final Node scene = new Node("scene");

	public StScene(Node rootNode) {
		rootNode.attachChild(scene);
	}

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

	public void create(Shape shape) {
		logger.debug("creating = {}", shape.name());
		Geometry geometry = geometry(shape);

		getState(StFocusCamera.class).focusOn(geometry);
		getState(StTransform.class).select(geometry);
		getState(StGui.class).propertiesFor(geometry);
	}

	Geometry geometry(Shape shape) {
		Geometry geometry = new Geometry(shape.name(), shape.mesh());
		geometry.setMaterial(new MtlLighting(getApplication().getAssetManager(), ColorRGBA.Gray));
		geometry.addControl(new CtShapeRef(shape));
		scene.attachChild(geometry);

		MouseEventControl.addListenersToSpatial(geometry, new DefaultMouseListener() {
			@Override
			protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
				if (event.getButtonIndex() != MouseInput.BUTTON_LEFT)
					return;

				event.setConsumed();

				getState(StFocusCamera.class).focusOn(geometry);
				getState(StTransform.class).select(geometry);

				getState(StGui.class).propertiesFor(geometry);
			}
		});
		return geometry;
	}

	public void saveToJson(File file) {
		getState(StIo.class).toJson(scene, file);
	}

	public void loadFromJson(File file) {
		scene.detachAllChildren();
		getState(StIo.class).fromJson(file);
	}

	public void saveToJ3o(File file) {
		getState(StIo.class).toJ3o(scene, file);
	}

	public void duplicate(Spatial subject) {
		Shape shape = subject.getControl(CtShapeRef.class).shape();

		Geometry dup = geometry(shape);
		dup.setLocalTranslation(subject.getLocalTranslation());
		dup.setLocalRotation(subject.getLocalRotation());
		dup.setLocalScale(subject.getLocalScale());

		dup.setName(subject.getName() + "-copy");

		getState(StFocusCamera.class).focusOn(dup);
		getState(StTransform.class).select(dup);
		getState(StGui.class).propertiesFor(dup);
	}

	public void delete(Spatial subject) {
		scene.detachChild(subject);
		getState(StTransform.class).clear();
	}

	public void clear() {
		scene.detachAllChildren();
		getState(StTransform.class).clear();
	}

}
