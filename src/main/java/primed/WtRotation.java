package primed;

import com.jme3.input.KeyInput;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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

import jme3.common.misc.Angles;
import jme3utilities.SimpleControl;
import primed.lemur.FloatField;

final class WtRotation extends Container {

	public WtRotation(Spatial subject) {
		super();

		addChild(new Label("rotation", new ElementId("window.title.label")));

		Container content = addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Even, FillMode.Last)));

		Angles angles = new Angles(subject.getLocalRotation().clone());

		content.addChild(new Label("x"));
		FloatField valx = content.addChild(new FloatField(angles.asDegreesX()), 1);

		content.addChild(new Label("y"));
		FloatField valy = content.addChild(new FloatField(angles.asDegreesY()), 1);

		content.addChild(new Label("z"));
		FloatField valz = content.addChild(new FloatField(angles.asDegreesZ()), 1);

		SimpleControl control = new SimpleControl() {
			@Override
			protected void controlUpdate(float updateInterval) {
				super.controlUpdate(updateInterval);

				Angles a = new Angles(subject.getLocalRotation().clone());
				
				valx.setText(a.degreesAsStringX());
				valy.setText(a.degreesAsStringY());
				valz.setText(a.degreesAsStringZ());

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
			float x = FastMath.DEG_TO_RAD * Float.valueOf(valx.getText());
			float y = FastMath.DEG_TO_RAD * Float.valueOf(valy.getText());
			float z = FastMath.DEG_TO_RAD * Float.valueOf(valz.getText());

			float[] a = new float[] { x, y, z };

			subject.setLocalRotation(new Quaternion(a));
		};

		valx.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valx.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
		valy.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valy.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
		valz.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valz.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
	}

}
