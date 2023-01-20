import com.greentree.commons.data.file.DataSerializer;
import com.greentree.commons.data.file.serializer.ArraySerializer;
import com.greentree.commons.data.file.serializer.BooleanSerializer;
import com.greentree.commons.data.file.serializer.CharSerializer;
import com.greentree.commons.data.file.serializer.ClassSerializer;
import com.greentree.commons.data.file.serializer.CollectionSerializer;
import com.greentree.commons.data.file.serializer.DoubleSerializer;
import com.greentree.commons.data.file.serializer.EnumSerializer;
import com.greentree.commons.data.file.serializer.FieldsSerializer;
import com.greentree.commons.data.file.serializer.FloatSerializer;
import com.greentree.commons.data.file.serializer.IntSerializer;
import com.greentree.commons.data.file.serializer.MapSerializer;
import com.greentree.commons.data.file.serializer.RecordSerializer;
import com.greentree.commons.data.file.serializer.SerializableSerializer;
import com.greentree.commons.data.file.serializer.StringSerializer;

open module commons.data {
	
	requires transitive commons.action;
	
	exports com.greentree.commons.data;
	
	exports com.greentree.commons.data.externalizable;
	exports com.greentree.commons.data.resource;
	exports com.greentree.commons.data.resource.location;
	exports com.greentree.commons.data.file;
	exports com.greentree.commons.data.file.serializer;
	
	uses DataSerializer;
	
	provides DataSerializer with StringSerializer, IntSerializer, FloatSerializer, DoubleSerializer,
			MapSerializer, ArraySerializer, FieldsSerializer, BooleanSerializer, ClassSerializer,
			CollectionSerializer, CharSerializer, EnumSerializer, RecordSerializer,
			SerializableSerializer;
}
