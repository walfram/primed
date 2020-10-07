package primed.shapes;

import java.util.Objects;

public abstract class Param<T> {

	private final String name;
	private final T value;

	protected Param(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public String name() {
		return name;
	}

	public boolean nameEquals(String string) {
		return Objects.equals(name, string);
	}

	public int intValue() {
		if (value instanceof Number)
			return ((Number) value).intValue();
		else {
			return 0;
		}
	}

	public float floatValue() {
		if (value instanceof Number) {
			return ((Number) value).floatValue();
		} else {
			return 0f;
		}
	}

	public boolean booleanValue() {
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		} else {
			return false;
		}
	}

	public String valueAsString() {
		return value.toString();
	}

	public abstract Param<?> newParam(String text);

	@Override
	public boolean equals(Object obj) {
		Param<?> other = (Param<?>) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public boolean isBoolean() {
		return (value instanceof Boolean);
	}

}
