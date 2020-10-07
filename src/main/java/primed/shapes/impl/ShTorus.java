package primed.shapes.impl;

import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Torus;

import common.FlatShadedMesh;
import primed.shapes.PmBoolean;
import primed.shapes.PmFloat;
import primed.shapes.PmInt;
import primed.shapes.Shape;

public final class ShTorus extends Shape {

	public ShTorus() {
		super(new PmInt("circleSamples", 8), new PmInt("radialSamples", 8), new PmFloat("minorRadius", 0.125f), new PmBoolean(
				"flatShaded", false));
	}

	@Override
	public String name() {
		return "Torus";
	}

	@Override
	public Mesh mesh() {
		int circleSamples = find("circleSamples").intValue();
		int radialSamples = find("radialSamples").intValue();
		float minorRadius = find("minorRadius").floatValue();

		boolean flatShaded = find("flatShaded").booleanValue();

		Mesh mesh = new Torus(circleSamples, radialSamples, minorRadius, 1f);

		if (flatShaded)
			return new FlatShadedMesh(mesh).create();

		return mesh;
	}

}
