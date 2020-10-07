package primed;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.anim.SpatialTweens;
import com.simsilica.lemur.anim.Tween;

public final class StFocusCamera extends BaseAppState {

	private final Node pivot = new Node("pivot");
	private ChaseCamera chaseCamera;

	public StFocusCamera(Node rootNode) {
		rootNode.attachChild(pivot);
	}

	@Override
	protected void initialize(Application app) {
		chaseCamera = new ChaseCamera(app.getCamera(), pivot, app.getInputManager());

		chaseCamera.setUpVector(Vector3f.UNIT_Y);
		chaseCamera.setInvertVerticalAxis(true);

		chaseCamera.setDefaultHorizontalRotation(FastMath.QUARTER_PI);

		chaseCamera.setMinVerticalRotation(-FastMath.HALF_PI);

		chaseCamera.setMaxDistance(200f);

		chaseCamera.setToggleRotationTrigger(
				new MouseButtonTrigger(MouseInput.BUTTON_RIGHT),
				new KeyTrigger(KeyInput.KEY_LMENU));
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

	public void focusOn(Spatial spatial) {
		Tween move = SpatialTweens.move(pivot, null, spatial.getLocalTranslation(), 0.25);
		GuiGlobals.getInstance().getAnimationState().add(move);
	}

	public void disableZoom() {
		chaseCamera.setZoomSensitivity(0f);
	}

	public void enableZoom() {
		chaseCamera.setZoomSensitivity(2f);
	}

}
