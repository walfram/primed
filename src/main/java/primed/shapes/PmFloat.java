package primed.shapes;

public final class PmFloat extends Param<Float> {

	public PmFloat(String name, Float value) {
		super(name, value);
	}

	@Override
	public Param<?> newParam(String text) {
		return new PmFloat(name(), Float.valueOf(text));
	}

}
