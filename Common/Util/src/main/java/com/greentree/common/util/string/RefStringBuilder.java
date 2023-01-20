package com.greentree.common.util.string;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public final class RefStringBuilder {

	private final StringPart[] str;

	private RefStringBuilder(String input) {
		Collection<StringPart> col = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\$\\{[_\\.\\d\\w]*\\}");
		var matcher = pattern.matcher(input);
		int end = 0;
		while(matcher.find()) {
			col.add(new TextStringPart(input.substring(end, matcher.start())));

			var name = input.substring(matcher.start()+2, matcher.end()-1);
			col.add(new VarStringPart(name));

			end = matcher.end();
		}
		col.add(new TextStringPart(input.substring(end)));
		str = new StringPart[col.size()];
		col.toArray(str);
	}
	public static RefStringBuilder build(InputStream in) throws IOException {
		try(in) {
			return new RefStringBuilder(new String(in.readAllBytes()));
		}
	}

	public static RefStringBuilder build(String str) {
		return new RefStringBuilder(str);
	}



	@Override
	public String toString() {
		return "RefStringBuilder [str=" + Arrays.toString(str) + "]";
	}



	public String toString(Function<String, String> p) {
		StringBuilder builder = new StringBuilder();
		for(var sp : str) builder.append(sp.getText(p));
		return builder.toString();
	}

	public String toString(Map<String, String> p) {
		return toString(s -> p.get(s));
	}

	private interface StringPart {

		String getText(Function<String, String> p);

	}

	private static class TextStringPart implements StringPart {

		private final String str;

		public TextStringPart(String str) {
			this.str = str;
		}

		@Override
		public String getText(Function<String, String> p) {
			return str;
		}

	}

	private static class VarStringPart implements StringPart {

		private final String str;

		public VarStringPart(String str) {
			this.str = str;
		}

		@Override
		public String getText(Function<String, String> p) {
			return p.apply(str);
		}

	}

}
