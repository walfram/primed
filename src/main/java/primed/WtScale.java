package primed;

import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.event.KeyAction;
import com.simsilica.lemur.event.KeyActionListener;
import com.simsilica.lemur.style.ElementId;

import jme3utilities.SimpleControl;
import primed.lemur.FloatField;

final class WtScale extends Container {

	public WtScale(Spatial subject) {
		super();

		addChild(new Label("scale", new ElementId("window.title.label")));

		Container content = addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Even, FillMode.Last)));

		content.addChild(new Label("x"));
		FloatField valx = content.addChild(new FloatField(subject.getLocalScale().x), 1);

		content.addChild(new Label("y"));
		FloatField valy = content.addChild(new FloatField(subject.getLocalScale().y), 1);

		content.addChild(new Label("z"));
		FloatField valz = content.addChild(new FloatField(subject.getLocalScale().z), 1);

		SimpleControl control = new SimpleControl() {
			@Override
			protected void controlUpdate(float updateInterval) {
				super.controlUpdate(updateInterval);

				Vector3f ls = subject.getLocalScale().clone();

				valx.setText(String.valueOf(ls.x));
				valy.setText(String.valueOf(ls.y));
				valz.setText(String.valueOf(ls.z));

			}
		};
		addControl(control);

		addControl(new SimpleControl() {
			@Override
			protected void controlUpdate(float updateInterval) {
				super.controlUpdate(updateInterval);

				Spatial focus = GuiGlobals.getInstance().getFocusManagerState().getFocus();

				if (focus == valx || focus == valy || focus == valz) {
					control.setEnabled(false);
				} else {
					control.setEnabled(true);
				}
			}
		});

		KeyActionListener upd = (source, key) -> {
			float x = Float.valueOf(valx.getText());
			float y = Float.valueOf(valy.getText());
			float z = Float.valueOf(valz.getText());
			subject.setLocalScale(x, y, z);
		};

		valx.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valx.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
		valy.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valy.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
		valz.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valz.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
	}

}
