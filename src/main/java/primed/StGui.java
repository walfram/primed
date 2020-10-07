package primed;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.style.ElementId;

import primed.shapes.Shapes;

final class StGui extends BaseAppState {

	private static final Logger logger = LoggerFactory.getLogger(StGui.class);

	private final Node gui = new Node("gui");

	public StGui(Node guiNode) {
		guiNode.attachChild(gui);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initialize(Application app) {
		Container left = new Container();
		gui.attachChild(left);
		left.setLocalTranslation(5, 800 - 5, 0);

		Container modes = new Container();
		modes.addChild(new Label("mode", new ElementId("window.title.label")));
		for (Mode mode : Mode.values()) {
			Button button = new Button(mode.name());
			button.addClickCommands(b -> getState(StTransform.class).mode(mode));
			modes.addChild(button);
		}
		left.addChild(modes);

		Container shapes = new Container();
		shapes.addChild(new Label("shapes", new ElementId("window.title.label")));
		new Shapes().asList().forEach(shape -> {
			Button button = new Button(shape.name());
			button.addClickCommands(b -> getState(StScene.class).create(shape));
			shapes.addChild(button);
		});
		left.addChild(shapes);

		Container menu = new Container(new SpringGridLayout(Axis.X, Axis.Y));

		menu.addChild(new Button("export")).addClickCommands(b -> save());
		menu.addChild(new Button("import")).addClickCommands(b -> load());
		menu.addChild(new Button("clear scene")).addClickCommands(
				b -> getState(StScene.class).clear(),
				b -> gui.detachChildNamed("subject-properties"));

		gui.attachChild(menu);
		menu.setLocalTranslation(app.getCamera().getWidth() * 0.5f - menu.getPreferredSize().x * 0.5f, 800 - 5, 0);
	}

	private void load() {
		File userDir = new File(System.getProperty("user.dir"), "data");
		// JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		JFileChooser jfc = new JFileChooser(userDir);
		jfc.setDialogTitle("Select PrimEd File...");
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PrimEd json files", "json");
		jfc.addChoosableFileFilter(filter);
		// int returnValue = jfc.showOpenDialog(null);
		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			logger.debug("loading from = {}", jfc.getSelectedFile());
			getState(StScene.class).load(jfc.getSelectedFile());
		}
	}

	private void save() {
		File userDir = new File(System.getProperty("user.dir"));
		// JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		JFileChooser jfc = new JFileChooser(userDir);
		jfc.setDialogTitle("Select PrimEd File...");
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PrimEd json files", "json");
		jfc.addChoosableFileFilter(filter);
		// int returnValue = jfc.showOpenDialog(null);
		int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			logger.debug("saving to = {}", jfc.getSelectedFile());
			getState(StScene.class).save(jfc.getSelectedFile());
		}
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

	@SuppressWarnings("unchecked")
	public void propertiesFor(Spatial subject) {
		Container properties = new Container();

		properties.addChild(new Label("properties: " + subject.getName(), new ElementId("window.title.label")));

		properties.addChild(new WtTranslation(subject));
		properties.addChild(new WtRotation(subject));
		properties.addChild(new WtScale(subject));

		properties.addChild(new WtProperties(subject));

		Container actions = properties.addChild(new Container());
		actions.addChild(new Label("actions", new ElementId("window.title.label")));
		actions.addChild(new Button("duplicate")).addClickCommands(b -> getState(StScene.class).duplicate(subject));
		actions.addChild(new Button("delete")).addClickCommands(
				b -> getState(StScene.class).delete(subject),
				b -> properties.removeFromParent());

		properties.setName("subject-properties");
		gui.detachChildNamed(properties.getName());

		gui.attachChild(properties);
		properties.setLocalTranslation(
				getApplication().getCamera().getWidth() - properties.getPreferredSize().x - 5,
				getApplication().getCamera().getHeight() - 5,
				0);
	}

}
