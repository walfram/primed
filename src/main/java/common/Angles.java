package common;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;

public final class Angles {

	private static final float PRECISION = 1f;
	private final Quaternion source;

	public Angles(Quaternion source) {
		this.source = source;
	}

	public float asDegreesX() {
		return Math.round(asRadiansX() * FastMath.RAD_TO_DEG * PRECISION) / PRECISION;
	}

	public float asDegreesY() {
		return Math.round(asRadiansY() * FastMath.RAD_TO_DEG * PRECISION) / PRECISION;
	}

	public float asDegreesZ() {
		return Math.round(asRadiansZ() * FastMath.RAD_TO_DEG * PRECISION) / PRECISION;
	}

	public String degreesAsStringX() {
		return String.valueOf(asDegreesX());
	}

	public String degreesAsStringY() {
		return String.valueOf(asDegreesY());
	}

	public String degreesAsStringZ() {
		return String.valueOf(asDegreesZ());
	}

	public float asRadiansX() {
		return source.toAngles(null)[0];
	}

	public float asRadiansY() {
		return source.toAngles(null)[1];
	}

	public float asRadiansZ() {
		return source.toAngles(null)[2];
	}

}
