package test.data

import com.greentree.commons.serialization.annotation.CustomSerializer
import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.data.decodeSerializable
import com.greentree.commons.serialization.data.encodeSerializable
import com.greentree.commons.serialization.descriptor.ReflectionSerialDescriptor
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.Serializer

@CustomSerializer(CustomPersonSerializer::class)
data class CustomPerson(val name: Name, val age: Int)

object CustomPersonSerializer : Serializer<CustomPerson> {

	override val descriptor: SerialDescriptor<CustomPerson>
		get() = ReflectionSerialDescriptor(CustomPerson::class.java)

	override fun deserialize(decoder: Decoder) = decoder.beginStructure(descriptor).use { struct ->
		val name = struct.field(0).decodeSerializable<Name>()
		val age = struct.field(1).decodeInt()
		CustomPerson(name, age)
	}

	override fun serialize(encoder: Encoder, value: CustomPerson) {
		encoder.beginStructure(descriptor).use { struct ->
			struct.field(0).encodeSerializable(Name::class.java, value.name)
			struct.field(1).encodeInt(value.age)
		}
	}
}