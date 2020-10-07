package primed.shapes;

public final class PmBoolean extends Param<Boolean> {

	public PmBoolean(String name, Boolean value) {
		super(name, value);
	}

	@Override
	public Param<?> newParam(String text) {
		return new PmBoolean(name(), Boolean.valueOf(text));
	}

}
