package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringSerializer extends PrimitiveSerializer {

	public StringSerializer() {
		super(String.class);
	}
	
	@Override
	public void write(Object obj, DataOutput out) throws IOException {
		final var str = (String) obj;
		out.writeUTF(str);
	}

	@Override
	public Object read(Class<?> cls, DataInput in) throws IOException {
		return in.readUTF();
	}

//	@Override
//	public <T> void write(T last, T obj, DataOutput out, FileWriter file) throws IOException {
//		final var a = (String) last;
//		final var b = (String) obj;
//		
//		final var ds = a.length() - b.length();
//		out.writeInt(ds);
//		
//		if(ds < 0) {
//			out.writeUTF(b.substring(a.length()));
//		}
//
//		final var mn = a.length() > b.length() ? b.length() : a.length();
//		int s = 0;
//		for(int i = 0; i < mn; i++) {
//			final var ac = a.charAt(i);
//			final var bc = b.charAt(i);
//			if(ac != bc) s++;
//		}
//		out.writeInt(s);
//		for(int i = 0; i < mn; i++) {
//			final var ac = a.charAt(i);
//			final var bc = b.charAt(i);
//			if(ac != bc) {
//				out.writeInt(i);
//				out.writeChar(bc);
//			}
//		}
//	}
//	
//	@Override
//	public Object read(Object last, TypeInfo<?> type, DataInput in, FileReader file) throws IOException {
//		final var a = (String) last;
//		final var ds = in.readInt();
//
//		final String end;
//		if(ds < 0)
//			end = in.readUTF();
//		else
//			end = "";
//		
//		final char[] arr;
//		if(ds > 0)
//			arr = new char[a.length() - ds];
//		else
//			arr = new char[a.length()];
//		
//		final var s = in.readInt();
//		for(int i = 0; i < s; i++) {
//			final var j = in.readInt();
//			final var c = in.readChar();
//			arr[j] = c;
//		}
//		
//		for(int i = 0; i < arr.length; i++) {
//			if(arr[i] == 0)
//				arr[i] = a.charAt(i);
//		}
//		
//		return new String(arr) + end;
//	}
	
}
