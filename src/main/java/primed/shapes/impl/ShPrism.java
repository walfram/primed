package primed.shapes.impl;

import com.jme3.scene.Mesh;

import jme3utilities.mesh.Prism;
import primed.shapes.PmInt;
import primed.shapes.Shape;

public final class ShPrism extends Shape {

	public ShPrism() {
		super(new PmInt("numSides", 8));
	}

	@Override
	public String name() {
		return "Prism";
	}

	@Override
	public Mesh mesh() {
		int numSides = find("numSides").intValue();
		return new Prism(numSides, 1f, 1f, true);
	}

}
