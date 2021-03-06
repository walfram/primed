package primed;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

final class StCameraLight extends BaseAppState {

	private final DirectionalLight light = new DirectionalLight();
	private final ColorRGBA color;

	public StCameraLight(Node rootNode, ColorRGBA color) {
		rootNode.addLight(light);
		this.color = color.clone();
	}

	@Override
	protected void initialize(Application app) {
		light.setColor(color);
		light.setDirection(app.getCamera().getDirection());
	}

	@Override
	public void update(float tpf) {
		super.update(tpf);
		light.setDirection(getApplication().getCamera().getDirection());
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
