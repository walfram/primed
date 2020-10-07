package primed;

import java.util.List;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

import jme3utilities.SimpleControl;
import primed.shapes.Param;
import primed.shapes.Shape;

final class CtShapeRef extends SimpleControl {

	private final Shape shape;

	public CtShapeRef(Shape shape) {
		this.shape = shape;
	}

	public List<Param<?>> params() {
		return shape.params();
	}

	public void reshape(Param<?> updated) {
		shape.bind(List.of(updated));

		Mesh mesh = shape.mesh();
		((Geometry) spatial).setMesh(mesh);
	}

	public Shape shape() {
		return shape;
	}

}
