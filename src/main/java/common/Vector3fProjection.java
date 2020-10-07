package common;

import com.jme3.math.Vector3f;

public final class Vector3fProjection {

	private final Vector3f source;

	public Vector3fProjection(Vector3f source) {
		this.source = source;
	}

	public Vector3f projected(Vector3f onto) {
		float numerator = source.dot(onto);
		float denominator = onto.dot(onto);
		return onto.mult(numerator / denominator);
	}

}
