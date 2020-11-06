package primed;

import java.util.List;

import com.jme3.input.KeyInput;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.event.KeyAction;
import com.simsilica.lemur.event.KeyActionListener;
import com.simsilica.lemur.style.ElementId;

import primed.common.CallbackCheckboxModel;
import primed.shapes.Param;
import primed.shapes.PmBoolean;
import primed.shapes.PmFloat;
import primed.shapes.PmInt;

final class WtProperties extends Container {

	public WtProperties(Spatial subject) {
		super();

		Label title = addChild(new Label("properties", new ElementId("window.title.label")));
		title.setMaxWidth(200f);

		Container content = addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Even, FillMode.Last)));

		List<Param<?>> params = subject.getControl(CtShapeRef.class).shape().params();

		for (Param<?> param : params) {
			content.addChild(new Label(param.name()));

			if (param instanceof PmInt || param instanceof PmFloat) {
				TextField field = new TextField(param.valueAsString());
				field.setSingleLine(true);

				KeyActionListener listener = (txt, key) -> {
					Param<?> newParam = param.newParam(field.getText());
					subject.getControl(CtShapeRef.class).reshape(newParam);
				};

				field.getActionMap().put(new KeyAction(KeyInput.KEY_RETURN), listener);
				field.getActionMap().put(new KeyAction(KeyInput.KEY_NUMPADENTER), listener);

				content.addChild(field, 1);
			} else if (param instanceof PmBoolean) {
				Checkbox field = new Checkbox("", new CallbackCheckboxModel(param.booleanValue(), (bool) -> {
					Param<?> newParam = param.newParam(String.valueOf(bool));
					subject.getControl(CtShapeRef.class).reshape(newParam);
				}));

				content.addChild(field, 1);
			} else {
				throw new IllegalArgumentException(String.format("unknown paramter type = %s", param.getClass()));
			}
		}
	}

}
