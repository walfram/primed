package common.material;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

public class MtlShowNormals extends Material {

	public MtlShowNormals(AssetManager assetManager) {
		super(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
	}

}
