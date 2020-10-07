package primed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.event.CursorEventControl;

import common.material.MtlLighting;
import jme3utilities.SimpleControl;
import jme3utilities.debug.BoundsVisualizer;
import jme3utilities.mesh.Octahedron;
import primed.transform.CameraZoomToggle;
import primed.transform.Rotate;
import primed.transform.Scale;
import primed.transform.Translate;

final class StTransform extends BaseAppState {

	private final class CtCopyTransform extends SimpleControl {
		private final Spatial subject;

		public CtCopyTransform(Spatial subject) {
			this.subject = subject;
		}

		@Override
		protected void controlUpdate(float updateInterval) {
			super.controlUpdate(updateInterval);
			spatial.setLocalTranslation(subject.getLocalTranslation());
			spatial.setLocalRotation(subject.getLocalRotation());
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(StTransform.class);
	private AssetManager assetManager;

	private final Node handles = new Node("handles");
	private final Node bounds = new Node("bounds");

	private Mode mode = Mode.TRANSLATE;

	public StTransform(Node rootNode) {
		rootNode.attachChild(handles);
		rootNode.attachChild(bounds);
	}

	@Override
	protected void initialize(Application app) {
		assetManager = app.getAssetManager();

		bounds.addControl(new BoundsVisualizer(assetManager));
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

	public void mode(Mode mode) {
		this.mode = mode;

		Spatial subject = bounds.getControl(BoundsVisualizer.class).getSubject();

		if (subject != null)
			select(subject);
	}

	public void select(Spatial subject) {
		logger.debug("selected = {}", subject);

		handles.detachAllChildren();

		bounds.getControl(BoundsVisualizer.class).setSubject(subject);
		bounds.getControl(BoundsVisualizer.class).setEnabled(true);

		if (mode == Mode.TRANSLATE) {
			translate(subject);
		} else if (mode == Mode.SCALE) {
			scale(subject);
		} else if (mode == Mode.ROTATE) {
			rotate(subject);
		}

		handles.removeControl(CtCopyTransform.class);
		handles.addControl(new CtCopyTransform(subject));
	}

	private void rotate(Spatial subject) {
		BoundingBox bound = (BoundingBox) subject.getWorldBound().clone();
		Cylinder mesh = new Cylinder(8, 8, 0.125f, 1f, true);

		Geometry handlex = handle(subject, Axis.X, mesh, bound);
		Geometry handley = handle(subject, Axis.Y, mesh, bound);
		Geometry handlez = handle(subject, Axis.Z, mesh, bound);

		handles.attachChild(handlex);
		handles.attachChild(handley);
		handles.attachChild(handlez);

		CursorEventControl.addListenersToSpatial(
				handlex,
				new Rotate(subject, Axis.X),
				new CameraZoomToggle(getState(StFocusCamera.class)));

		CursorEventControl.addListenersToSpatial(
				handley,
				new Rotate(subject, Axis.Y),
				new CameraZoomToggle(getState(StFocusCamera.class)));

		CursorEventControl.addListenersToSpatial(
				handlez,
				new Rotate(subject, Axis.Z),
				new CameraZoomToggle(getState(StFocusCamera.class)));
	}

	private void scale(Spatial subject) {
		BoundingBox bound = (BoundingBox) subject.getWorldBound().clone();
		Octahedron mesh = new Octahedron(0.25f, true);

		Geometry handlex = handle(subject, Axis.X, mesh, bound);
		Geometry handley = handle(subject, Axis.Y, mesh, bound);
		Geometry handlez = handle(subject, Axis.Z, mesh, bound);

		handles.attachChild(handlex);
		handles.attachChild(handley);
		handles.attachChild(handlez);

		CursorEventControl.addListenersToSpatial(handlex, new Scale(subject, Axis.X));
		CursorEventControl.addListenersToSpatial(handley, new Scale(subject, Axis.Y));
		CursorEventControl.addListenersToSpatial(handlez, new Scale(subject, Axis.Z));
	}

	private void translate(Spatial subject) {
		BoundingBox bound = (BoundingBox) subject.getWorldBound().clone();
		Box mesh = new Box(0.125f, 0.125f, 0.5f);

		Geometry handlex = handle(subject, Axis.X, mesh, bound);
		Geometry handley = handle(subject, Axis.Y, mesh, bound);
		Geometry handlez = handle(subject, Axis.Z, mesh, bound);

		handles.attachChild(handlex);
		handles.attachChild(handley);
		handles.attachChild(handlez);

		CursorEventControl.addListenersToSpatial(handlex, new Translate(subject, Axis.X));
		CursorEventControl.addListenersToSpatial(handley, new Translate(subject, Axis.Y));
		CursorEventControl.addListenersToSpatial(handlez, new Translate(subject, Axis.Z));
	}

	private Geometry handle(Spatial subject, Axis axis, Mesh mesh, BoundingBox bound) {
		Geometry handle = new Geometry(axis.name(), mesh);
		handle.setLocalRotation(new Quaternion().lookAt(axis.getDirection(), Vector3f.UNIT_Y));
		handle.setLocalTranslation(bound.getExtent(null).mult(axis.getDirection()));

		Vector3f c = axis.getDirection().mult(0.5f);
		handle.setMaterial(new MtlLighting(assetManager, new ColorRGBA(c.x, c.y, c.z, 1f)));

		return handle;
	}

	public void clear() {
		handles.detachAllChildren();
		
		bounds.getControl(BoundsVisualizer.class).setSubject(null);
		bounds.getControl(BoundsVisualizer.class).setEnabled(false);
	}

}
