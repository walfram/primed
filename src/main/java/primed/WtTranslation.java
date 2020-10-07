package primed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.GuiControl;
import com.simsilica.lemur.event.KeyAction;
import com.simsilica.lemur.event.KeyActionListener;
import com.simsilica.lemur.focus.FocusChangeEvent;
import com.simsilica.lemur.focus.FocusChangeListener;
import com.simsilica.lemur.style.ElementId;

import jme3utilities.SimpleControl;
import primed.lemur.FloatField;

final class WtTranslation extends Container {

	private static final Logger logger = LoggerFactory.getLogger(WtTranslation.class);
	
	public WtTranslation(Spatial subject) {
		super();
		
		addChild(new Label("translation", new ElementId("window.title.label")));
		
		Container content = addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Even, FillMode.Last)));

		content.addChild(new Label("x"));
		FloatField valx = content.addChild(new FloatField(subject.getLocalTranslation().x), 1);
		
		valx.getControl(GuiControl.class).addFocusChangeListener(new FocusChangeListener() {

			@Override
			public void focusGained(FocusChangeEvent event) {
				logger.debug("focus gained");
			}

			@Override
			public void focusLost(FocusChangeEvent event) {
				logger.debug("focus lost");
			}
			
		});

		content.addChild(new Label("y"));
		FloatField valy = content.addChild(new FloatField(subject.getLocalTranslation().y), 1);

		content.addChild(new Label("z"));
		FloatField valz = content.addChild(new FloatField(subject.getLocalTranslation().z), 1);

		SimpleControl control = new SimpleControl() {
			@Override
			protected void controlUpdate(float updateInterval) {
				super.controlUpdate(updateInterval);

				Vector3f lt = subject.getLocalTranslation().clone();
				
				valx.setText(String.valueOf(lt.x));
				valy.setText(String.valueOf(lt.y));
				valz.setText(String.valueOf(lt.z));

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
			subject.setLocalTranslation(x, y, z);
		};
		
		valx.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valx.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
		valy.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valy.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
		valz.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), upd);
		valz.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), upd);
	}

}
