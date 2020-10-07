package primed;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;

public class Main extends SimpleApplication {

	public static void main(String[] args) {
		Main app = new Main();

		AppSettings settings = new AppSettings(true);
		settings.setResolution(1600, 800);

		app.setSettings(settings);
		app.setShowSettings(false);

		app.start();
	}

	@Override
	public void simpleInitApp() {
		stateManager.attach(new StInit(rootNode));
		stateManager.attach(new StCameraLight(rootNode, ColorRGBA.White));

		stateManager.attach(new StFocusCamera(rootNode));

		stateManager.attach(new StScene(rootNode));
		stateManager.attach(new StTransform(rootNode));
		stateManager.attach(new StIo());
		stateManager.attach(new StGui(guiNode));
	}

}
