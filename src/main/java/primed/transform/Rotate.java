package primed.transform;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.event.CursorButtonEvent;
import com.simsilica.lemur.event.CursorListener;
import com.simsilica.lemur.event.CursorMotionEvent;

public final class Rotate implements CursorListener {

	private final Spatial subject;
	private final Axis axis;

	public Rotate(Spatial subject, Axis axis) {
		this.subject = subject;
		this.axis = axis;
	}

	@Override
	public void cursorButtonEvent(CursorButtonEvent event, Spatial target, Spatial capture) {
	}

	@Override
	public void cursorEntered(CursorMotionEvent event, Spatial target, Spatial capture) {
	}

	@Override
	public void cursorExited(CursorMotionEvent event, Spatial target, Spatial capture) {
	}

	@Override
	public void cursorMoved(CursorMotionEvent event, Spatial target, Spatial capture) {
		if (event.getScrollDelta() == 0)
			return;

		event.setConsumed();

		Vector3f localAxis = subject.getLocalRotation().mult(axis.getDirection());

		float signum = Math.signum(event.getScrollDelta());

		Quaternion q = new Quaternion().fromAngleAxis(signum * 5f * FastMath.DEG_TO_RAD, localAxis);
		Quaternion lr = q.mult(subject.getLocalRotation());
		subject.setLocalRotation(lr);

		target.getParent().setLocalRotation(lr);
	}

}
