package primed.shapes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jme3.scene.Mesh;

public abstract class Shape {

	protected final Map<String, Param<?>> params = new LinkedHashMap<>();

	protected Shape(Param<?>... params) {
		for (Param<?> param : params) {
			this.params.put(param.name(), param);
		}
	}

	public abstract String name();

	public List<Param<?>> params() {
		return new ArrayList<>(params.values());
	}

	public abstract Mesh mesh();

	public Param<?> find(String paramName) {
		return params.get(paramName);
	}

	public void bind(List<Param<?>> source) {
		for (Param<?> param : source) {
			this.params.put(param.name(), param);
		}
	}

}
