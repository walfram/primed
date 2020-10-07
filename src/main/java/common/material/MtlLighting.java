package common.material;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

public final class MtlLighting extends Material {

	public MtlLighting(AssetManager assetManager, ColorRGBA diffuse, ColorRGBA ambient) {
		super(assetManager, Materials.LIGHTING);
		setBoolean("UseMaterialColors", true);
		setColor("Diffuse", diffuse);
		setColor("Ambient", ambient);
	}

	public MtlLighting(AssetManager assetManager, ColorRGBA color) {
		this(assetManager, color, color);
	}

	public MtlLighting(AssetManager assetManager, ColorRGBA color, Texture texture) {
		this(assetManager, color);
		setTexture("DiffuseMap", texture);
	}

	public MtlLighting(AssetManager assetManager, Texture texture) {
		super(assetManager, Materials.LIGHTING);
		setTexture("DiffuseMap", texture);
	}

	public MtlLighting(AssetManager assetManager, ColorRGBA color, String texturePath) {
		this(assetManager, color);
		setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
	}

}
