package primed.shapes.impl;

import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;

import common.FlatShadedMesh;
import primed.shapes.PmBoolean;
import primed.shapes.PmInt;
import primed.shapes.Shape;

public final class ShSphere extends Shape {

	public ShSphere() {
		super(new PmInt("zSamples", 8), new PmInt("radialSamples", 8), new PmBoolean("flatShaded", false));
	}

	@Override
	public String name() {
		return "Sphere";
	}

	@Override
	public Mesh mesh() {
		int zSamples = find("zSamples").intValue();
		int radialSamples = find("radialSamples").intValue();

		boolean flatShaded = find("flatShaded").booleanValue();

		Sphere sphere = new Sphere(zSamples, radialSamples, 1f);

		if (flatShaded)
			return new FlatShadedMesh(sphere).create();

		return sphere;
	}

}
