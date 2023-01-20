import com.greentree.data.file.DataSerializer;
import com.greentree.data.file.serializer.ArraySerializer;
import com.greentree.data.file.serializer.BooleanSerializer;
import com.greentree.data.file.serializer.CharSerializer;
import com.greentree.data.file.serializer.ClassSerializer;
import com.greentree.data.file.serializer.CollectionSerializer;
import com.greentree.data.file.serializer.DoubleSerializer;
import com.greentree.data.file.serializer.EnumSerializer;
import com.greentree.data.file.serializer.FieldsSerializer;
import com.greentree.data.file.serializer.FloatSerializer;
import com.greentree.data.file.serializer.IntSerializer;
import com.greentree.data.file.serializer.MapSerializer;
import com.greentree.data.file.serializer.RecordSerializer;
import com.greentree.data.file.serializer.SerializableSerializer;
import com.greentree.data.file.serializer.StringSerializer;

open module common.data {
	
	requires transitive common.util;
	requires transitive common.action;
	
	exports com.greentree.data;
	
	exports com.greentree.data.externalizable;
	exports com.greentree.data.resource;
	exports com.greentree.data.resource.location;
	exports com.greentree.data.file;
	exports com.greentree.data.file.serializer;
	
	uses DataSerializer;
	
	provides DataSerializer with StringSerializer, IntSerializer, FloatSerializer, DoubleSerializer,
			MapSerializer, ArraySerializer, FieldsSerializer, BooleanSerializer, ClassSerializer,
			CollectionSerializer, CharSerializer, EnumSerializer, RecordSerializer,
			SerializableSerializer;
}
