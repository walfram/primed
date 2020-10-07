package common;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;

public final class NdDebugGrid extends Node {

	public NdDebugGrid(AssetManager assetManager, int yLines, int xLines, float distance, ColorRGBA color) {
		super("debug-grid-node");

		Geometry geometry = new Geometry("debug-grid-geometry", new Grid(xLines, yLines, distance));

		Material material = new Material(assetManager, Materials.UNSHADED);
		material.setColor("Color", color);
		geometry.setMaterial(material);

		geometry.center();

		attachChild(geometry);
	}

}
