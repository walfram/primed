package primed.transform;

import com.jme3.scene.Spatial;
import com.simsilica.lemur.event.CursorButtonEvent;
import com.simsilica.lemur.event.CursorListener;
import com.simsilica.lemur.event.CursorMotionEvent;

import primed.StFocusCamera;

public final class CameraZoomToggle implements CursorListener {

	private final StFocusCamera state;

	public CameraZoomToggle(StFocusCamera state) {
		this.state = state;
	}

	@Override
	public void cursorButtonEvent(CursorButtonEvent event, Spatial target, Spatial capture) {
	}

	@Override
	public void cursorEntered(CursorMotionEvent event, Spatial target, Spatial capture) {
		state.disableZoom();
	}

	@Override
	public void cursorExited(CursorMotionEvent event, Spatial target, Spatial capture) {
		state.enableZoom();
	}

	@Override
	public void cursorMoved(CursorMotionEvent event, Spatial target, Spatial capture) {
	}

}
