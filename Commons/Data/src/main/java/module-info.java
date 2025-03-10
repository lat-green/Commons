import com.greentree.commons.data.file.DataSerializer;
import com.greentree.commons.data.file.serializer.*;

open module commons.data {
    requires transitive commons.action;
    requires transitive commons.reflection;
    requires kotlin.stdlib;
    requires jdk.jfr;
    exports com.greentree.commons.data;
    exports com.greentree.commons.data.externalizable;
    exports com.greentree.commons.data.resource;
    exports com.greentree.commons.data.resource.location;
    exports com.greentree.commons.data.file;
    exports com.greentree.commons.data.file.serializer;
    exports com.greentree.commons.data.react;
    uses DataSerializer;
    provides DataSerializer with StringSerializer, IntSerializer, FloatSerializer, DoubleSerializer,
            MapSerializer, ArraySerializer, FieldsSerializer, BooleanSerializer, ClassSerializer,
            CollectionSerializer, CharSerializer, EnumSerializer, RecordSerializer,
            SerializableSerializer;
}
