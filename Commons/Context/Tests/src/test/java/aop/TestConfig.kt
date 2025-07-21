package aop

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.context.layer.BeanLayer
import com.greentree.commons.context.registerInstance
import com.greentree.commons.context.resolveAllBeans

class TestConfig : BeanLayer {

	override fun MutableBeanContext.register() {
		registerInstance(Arseny)
		registerInstance(Anton)
		registerInstance(Rect(15f))
		registerInstance(Triangle(15f))
		for(name in resolveAllBeans<Name>().toList()) {
			registerInstance(NamePerson(name))
		}
	}
}
