package com.greentree.commons.data.compress;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ZeroCopressor implements ByteCompressor {

	@Override
	public byte[] compress(InputStream bin) throws IOException {
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var out = new DataOutputStream(bout);
			final var in = new DataInputStream(bin);) {
        		int zCount = 0;
        		while(in.available() > 0) {
        			final var v = in.readByte();
        			if(v == 0)
        				zCount++;
        			else {
        				if(zCount != 0) {
            				out.writeByte(0);
            				out.writeInt(zCount);
            				zCount = 0;
            			}
        				out.writeByte(v);
        			}
        		}
        		if(zCount != 0) {
    				out.writeByte(0);
    				out.writeInt(zCount);
            		zCount = 0;
        		}
			}
			return bout.toByteArray();
		}
	}

	@Override
	public byte[] uncompress(InputStream bin) throws IOException {
		try(final var bout = new ByteArrayOutputStream();) {
			try(final var out = new DataOutputStream(bout);
					final var in = new DataInputStream(bin);) {

				while(in.available() > 0) {
					final var b = in.readByte();
					if(b == 0) {
						final var c = in.readInt();
						out.write(new byte[c]);
					}else
						out.writeByte(b);
				}

			}
			return bout.toByteArray();
		}

	}

}
