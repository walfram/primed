package primed.transform;

import com.jme3.input.MouseInput;
import com.jme3.math.Plane;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.event.CursorButtonEvent;
import com.simsilica.lemur.event.CursorListener;
import com.simsilica.lemur.event.CursorMotionEvent;

import common.Vector3fProjection;

public final class Translate implements CursorListener {

	private final Spatial subject;
	private final Axis axis;

	private boolean translating = false;

	private Vector3f origin;

	public Translate(Spatial subject, Axis axis) {
		this.subject = subject;
		this.axis = axis;
	}

	@Override
	public void cursorButtonEvent(CursorButtonEvent event, Spatial target, Spatial capture) {
		if (event.getButtonIndex() != MouseInput.BUTTON_LEFT)
			return;

		event.setConsumed();

		if (event.isPressed()) {
			translating = true;
			origin = subject.getWorldTranslation().clone();
		} else {
			translating = false;
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
		if (!translating)
			return;

		Camera camera = event.getViewPort().getCamera();
		Vector2f click2d = event.getLocation().clone();

		Vector3f normal = new Vector3f();
		if (axis == Axis.X || axis == Axis.Z) {
			normal.set(Axis.Y.getDirection());
		} else {
			normal.set(camera.getDirection().negate());
		}

		Plane plane = new Plane();
		plane.setOriginNormal(origin, normal);

		Vector3f click3d = camera.getWorldCoordinates(click2d, 0f).clone();
		Vector3f dir = camera.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();

		Ray ray = new Ray(click3d, dir);
		Vector3f v = new Vector3f();
		ray.intersectsWherePlane(plane, v);

		v.subtractLocal(capture.getLocalTranslation());

		Vector3f projected = new Vector3fProjection(v).projected(axis.getDirection());
		Vector3f lt = subject.getLocalTranslation().clone();
		lt.set(axis.index(), projected.get(axis.index()));
		subject.setLocalTranslation(lt);

		capture.getParent().setLocalTranslation(lt);
	}

}
