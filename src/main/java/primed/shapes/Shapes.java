package primed.shapes;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import primed.shapes.impl.ShBox;
import primed.shapes.impl.ShCylinder;
import primed.shapes.impl.ShOctasphere;
import primed.shapes.impl.ShPrism;
import primed.shapes.impl.ShSphere;
import primed.shapes.impl.ShTorus;

public class Shapes {

	public List<Shape> asList() {
		return Arrays.asList(new ShBox(), new ShCylinder(), new ShOctasphere(), new ShPrism(), new ShSphere(), new ShTorus());
	}

	public Shape forClass(String shapeClassname) {
		try {
			Shape shape = (Shape) Class.forName(shapeClassname).getDeclaredConstructor().newInstance();
			return shape;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
