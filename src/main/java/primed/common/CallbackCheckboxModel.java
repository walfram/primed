package primed.common;

import java.util.function.Consumer;

import com.simsilica.lemur.DefaultCheckboxModel;
import com.simsilica.lemur.core.VersionedReference;

public final class CallbackCheckboxModel extends DefaultCheckboxModel {

	private final VersionedReference<Boolean> ref;
	private final Consumer<Boolean> callback;

	public CallbackCheckboxModel(boolean value, Consumer<Boolean> callback) {
		super(value);

		this.callback = callback;

		ref = createReference();
	}

	@Override
	public void setChecked(boolean state) {
		super.setChecked(state);

		if (ref.update()) {
			callback.accept(ref.get());
		}
	}

}
