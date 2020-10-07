package primed.shapes;

public final class PmInt extends Param<Integer> {

	public PmInt(String name, Integer value) {
		super(name, value);
	}

	@Override
	public Param<?> newParam(String text) {
		return new PmInt(name(), Integer.valueOf(text));
	}

}
