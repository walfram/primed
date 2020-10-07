package primed.lemur;

import com.google.common.base.CharMatcher;
import com.simsilica.lemur.text.DocumentModelFilter;

final class FloatFilter extends DocumentModelFilter {

	public FloatFilter(float input) {
		setInputTransform((Character in) -> {
			boolean matches = CharMatcher.anyOf("-.0123456789").matches(in);

			if (matches)
				return in;

			return null;
		});
		
		setText(String.valueOf(input));
	}

}
