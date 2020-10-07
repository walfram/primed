package primed.shapes.impl;

import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

import primed.shapes.Shape;

public final class ShBox extends Shape {

	public ShBox() {
		super();
	}

	@Override
	public String name() {
		return "Box";
	}

	@Override
	public Mesh mesh() {
		return new Box(1f, 1f, 1f);
	}

}
