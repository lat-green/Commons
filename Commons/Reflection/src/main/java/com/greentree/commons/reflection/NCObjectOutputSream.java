package com.greentree.commons.reflection;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Modifier;


public class NCObjectOutputSream extends OutputStream {

	private final OutputStream out;

	public NCObjectOutputSream(OutputStream out) throws IOException {
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	public void writeObject(Object obj) {
		if(obj instanceof Externalizable) return;
		final var cls = obj.getClass();
		try {
			final var wm = cls.getDeclaredMethod("writeObject", ObjectOutput.class);
//			System.out.println(wm);
			return;
		}catch(NoSuchMethodException | SecurityException e) {
		}
		try {
			final var wm = cls.getDeclaredMethod("writeObject", ObjectOutputStream.class);

			return;
		}catch(NoSuchMethodException | SecurityException e) {
		}
		try {
			final var wm = cls.getDeclaredMethod("writeObject", OutputStream.class);
//			System.out.println(wm);
			return;
		}catch(NoSuchMethodException | SecurityException e) {
		}
//		System.out.println(obj);
		for(var f : ClassUtil.getAllNotStaticFields(obj.getClass())) {
			final var m = f.getModifiers();
			if(Modifier.isTransient(m)) continue;
//			System.out.println(f);
		}
	}

}
