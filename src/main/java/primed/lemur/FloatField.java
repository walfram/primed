package primed.lemur;

import com.simsilica.lemur.TextField;

public final class FloatField extends TextField {
	
	public FloatField(float f) {
		super(new FloatFilter(f));
		
		setPreferredWidth(100f);
		
		setSingleLine(true);
	}

}
