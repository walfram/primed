package primed.shapes.impl;

import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Cylinder;

import jme3.common.mesh.FlatShaded;
import primed.shapes.PmBoolean;
import primed.shapes.PmFloat;
import primed.shapes.PmInt;
import primed.shapes.Shape;

public final class ShCylinder extends Shape {

	public ShCylinder() {
		super(new PmInt("axisSamples", 8), new PmInt("radialSamples", 8), new PmFloat("topRadius", 0.25f), new PmFloat(
				"bottomRadius", 1f), new PmBoolean("flatShaded", false));
	}

	@Override
	public String name() {
		return "Cylinder";
	}

	@Override
	public Mesh mesh() {
		int axisSamples = find("axisSamples").intValue();
		int radialSamples = find("radialSamples").intValue();
		float topRadius = find("topRadius").floatValue();
		float bottomRadius = find("bottomRadius").floatValue();

		boolean flatShaded = find("flatShaded").booleanValue();

		Cylinder cylinder = new Cylinder(axisSamples, radialSamples, topRadius, bottomRadius, 1f, true, false);

		if (flatShaded)
			return new FlatShaded(cylinder).mesh();

		return cylinder;
	}

}
