package com.greentree.commons.data.file;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.util.cortege.Pair;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface DataSerializer {

    TypeInfo<?> root();

    default <T> void write(T last, T obj, DataOutput out, DataFileWriter file) throws IOException {
        write(obj, out, file);
    }

    void write(Object obj, DataOutput out, DataFileWriter file) throws IOException;

    default Pair<Object, Runnable> read(Object last, TypeInfo<?> type, DataInput in, DataFileReader file) throws IOException {
        return read(type, in, file);
    }

    Pair<Object, Runnable> read(TypeInfo<?> type, DataInput in, DataFileReader file) throws IOException;

    static byte[] realloc(byte[] arr, int dlen) {
        assert dlen > 0;
        final var res = new byte[arr.length + dlen];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }

    default int priority(TypeInfo<?> type) {
        return 0;
    }

}
