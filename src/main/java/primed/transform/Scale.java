package primed.transform;

import com.jme3.bounding.BoundingBox;
import com.jme3.input.MouseInput;
import com.jme3.math.Plane;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.event.CursorButtonEvent;
import com.simsilica.lemur.event.CursorListener;
import com.simsilica.lemur.event.CursorMotionEvent;

import common.Vector3fProjection;

public final class Scale implements CursorListener {

	private final Spatial subject;
	private final Axis axis;

	private boolean scaling = false;
	private Vector3f origin;

	public Scale(Spatial subject, Axis axis) {
		this.subject = subject;
		this.axis = axis;
	}

	@Override
	public void cursorButtonEvent(CursorButtonEvent event, Spatial target, Spatial capture) {
		if (event.getButtonIndex() != MouseInput.BUTTON_LEFT)
			return;

		event.setConsumed();

		if (event.isPressed()) {
			scaling = true;
			origin = capture.getWorldTranslation().clone();
		} else {
			scaling = false;
		}
	}

	@Override
	public void cursorEntered(CursorMotionEvent event, Spatial target, Spatial capture) {
	}

	@Override
	public void cursorExited(CursorMotionEvent event, Spatial target, Spatial capture) {
	}

	@Override
	public void cursorMoved(CursorMotionEvent event, Spatial target, Spatial capture) {
		if (!scaling)
			return;

		Camera camera = event.getViewPort().getCamera();
		Vector2f click2d = event.getLocation().clone();

		Plane plane = new Plane();
		plane.setOriginNormal(origin, camera.getDirection().negate());

		Vector3f click3d = camera.getWorldCoordinates(click2d, 0f).clone();
		Vector3f dir = camera.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();

		Ray ray = new Ray(click3d, dir);
		Vector3f v = new Vector3f();
		ray.intersectsWherePlane(plane, v);

		Vector3f local = capture.getParent().worldToLocal(v, null);
		Vector3f projected = new Vector3fProjection(local).projected(axis.getDirection());
		capture.setLocalTranslation(projected);

		BoundingBox bound = (BoundingBox) ((Geometry) subject).getModelBound().clone();
		Vector3f extent = bound.getExtent(null);

		float distance = capture.getWorldTranslation().distance(subject.getWorldTranslation());
		float scale = distance / extent.get(axis.index());
		Vector3f ls = subject.getLocalScale().clone();
		ls.set(axis.index(), scale);
		subject.setLocalScale(ls);
	}

}
