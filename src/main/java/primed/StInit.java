package primed;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

import common.NdDebugGrid;
import jme3utilities.MyCamera;
import jme3utilities.debug.AxesVisualizer;

final class StInit extends BaseAppState {

	private final Node rootNode;

	public StInit(Node rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	protected void initialize(Application app) {
		GuiGlobals.initialize(app);
		BaseStyles.loadGlassStyle();
		GuiGlobals.getInstance().getStyles().setDefaultStyle(BaseStyles.GLASS);

		getState(FlyCamAppState.class).getCamera().setDragToRotate(true);
		getState(FlyCamAppState.class).getCamera().setMoveSpeed(20f);
		getState(FlyCamAppState.class).getCamera().setZoomSpeed(0f);
		getState(FlyCamAppState.class).setEnabled(false);

		app.getCamera().setLocation(new Vector3f(11.717239f, 5.9019957f, 11.698552f));
		app.getCamera().setRotation(new Quaternion(-0.05859966f, 0.9319393f, -0.17492728f, -0.31217888f));

		app.getViewPort().setBackgroundColor(ColorRGBA.DarkGray);
		MyCamera.setNearFar(app.getCamera(), 0.1f, 32768f);

		AxesVisualizer axesVisualizer = new AxesVisualizer(app.getAssetManager(), 250f, 1f);
		rootNode.addControl(axesVisualizer);
		axesVisualizer.setEnabled(true);

		rootNode.attachChild(new NdDebugGrid(app.getAssetManager(), 100, 100, 10f, ColorRGBA.Gray));

		rootNode.addLight(new AmbientLight(ColorRGBA.White));
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

}
