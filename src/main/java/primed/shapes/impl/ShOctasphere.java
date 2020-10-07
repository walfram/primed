package primed.shapes.impl;

import com.jme3.scene.Mesh;

import common.FlatShadedMesh;
import jme3utilities.mesh.Octasphere;
import primed.shapes.PmBoolean;
import primed.shapes.PmInt;
import primed.shapes.Shape;

public final class ShOctasphere extends Shape {

	public ShOctasphere() {
		super(new PmInt("numRefineSteps", 2), new PmBoolean("flatShaded", false));
	}

	@Override
	public String name() {
		return "Octasphere";
	}

	@Override
	public Mesh mesh() {
		int numRefineSteps = find("numRefineSteps").intValue();

		boolean flatShaded = find("flatShaded").booleanValue();

		Octasphere octasphere = new Octasphere(numRefineSteps, 1f);

		if (flatShaded)
			return new FlatShadedMesh(octasphere).create();

		return octasphere;
	}

}
