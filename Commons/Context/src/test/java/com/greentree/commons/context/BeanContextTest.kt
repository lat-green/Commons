package com.greentree.commons.context

import com.greentree.commons.context.layer.FuseBeanLayer
import com.greentree.commons.context.layer.registerLayer
import com.greentree.commons.context.mock.HiRepository1
import com.greentree.commons.context.mock.HiRepository2
import com.greentree.commons.context.provider.ProxySingletonBeanProvider
import com.greentree.commons.util.iterator.size
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.greentree.commons.context.mock.A
import com.greentree.commons.context.mock.AImpl
import com.greentree.commons.context.mock.B
import com.greentree.commons.context.mock.BImpl
import com.greentree.commons.context.mock.GroupService
import com.greentree.commons.context.mock.HiService
import com.greentree.commons.context.mock.TextRepository

class BeanContextTest {

	@Test
	fun singletonSame() {
		val ctx = BeanContext().apply {
			registerSingleton {
				HiService(resolveBean())
			}
			registerSingleton {
				HiRepository1()
			}
		}
		val a: HiService = ctx.resolveBean()
		val b: HiService = ctx.resolveBean()
		assertSame(a, b)
	}

	@Test
	fun singletonSame1() {
		val ctx = BeanContext().apply {
			registerSingleton {
				HiRepository1()
			}
			registerSingleton {
				GroupService(resolveAllBeans())
			}
			registerSingleton {
				HiRepository2()
			}
		}
		val a: GroupService = ctx.resolveBean()
		assertEquals(a.repositories.size, 2)
	}

	@Test
	fun singletonInPrototypeSame() {
		val ctx = BeanContext().apply {
			registerPrototype {
				HiService(resolveBean())
			}
			registerSingleton {
				HiRepository1()
			}
		}
		val a: HiService = ctx.resolveBean()
		val b: HiService = ctx.resolveBean()
		assertSame(a.repository, b.repository)
	}

	@Test
	fun prototypeInSingletonSame() {
		val ctx = BeanContext().apply {
			registerSingleton {
				HiService(resolveBean())
			}
			registerPrototype {
				HiRepository1()
			}
		}
		val a: HiService = ctx.resolveBean()
		val b: HiService = ctx.resolveBean()
		assertSame(a.repository, b.repository)
	}

	@Test
	fun prototypeNotSame() {
		val ctx = BeanContext().apply {
			registerPrototype {
				HiRepository1()
			}
		}
		val a: HiRepository1 = ctx.resolveBean()
		val b: HiRepository1 = ctx.resolveBean()
		assertNotSame(a, b)
	}

	@Test
	fun duplicateNameException() {
		val ctx = BeanContext().apply {
			registerSingleton {
				HiService(resolveBean())
			}
			assertThrows(Throwable::class.java) {
				registerSingleton {
					HiService(resolveBean())
				}
			}
		}
	}

	@Test
	fun registerFuseBeanLayer() {
		val ctx = BeanContext().apply {
			registerLayer(FuseBeanLayer)
		}
	}

	@Test
	fun proxySingletonBeanProvider() {
		val ctx = BeanContext().apply {
			register("a", ProxySingletonBeanProvider({
				AImpl(resolveBean())
			}))
			register("b", ProxySingletonBeanProvider({
				BImpl(resolveBean())
			}))
		}
		ctx.run {
			val a: A = resolveBean()
			val b: B = resolveBean()
			assertEquals(a.b, b)
			assertEquals(b.a, a)
		}
	}

	@Test
	fun resolveBeanContext() {
		val ctx = BeanContext().apply {
			registerPrototype {
				this
			}
		}

		assertSame(ctx, ctx.resolveBean<BeanContext>())
	}

	@Test
	fun resolvePrototypeBeanContextParent() {
		val parent = BeanContext().apply {
			registerPrototype {
				this
			}
		}
		val child = parent.child().apply {
		}
		assertSame(parent, parent.resolveBean<BeanContext>())
		assertSame(child, child.resolveBean<BeanContext>())
	}

	@Test
	fun resolveTransientBeanContextParent() {
		val parent = BeanContext().apply {
			registerTransient {
				this
			}
		}
		val child = parent.child().apply {
		}
		assertSame(parent, parent.resolveBean<BeanContext>())
		assertSame(child, child.resolveBean<BeanContext>())
	}

	@Test
	fun resolveInParent() {
		val parent = BeanContext().apply {
			registerTransient {
				HiService(resolveBean())
			}
			registerSingleton {
				TextRepository("parent")
			}
		}
		val child = parent.child().apply {
			registerSingleton {
				TextRepository("child")
			}
		}
		val parentService: HiService = parent.resolveBean()
		assertEquals(parentService.repository.hello(), "Hi parent")
		val childService: HiService = child.resolveBean()
		assertEquals(childService.repository.hello(), "Hi child")
	}

	@Test
	fun resolveInParentEquals() {
		val parent = BeanContext().apply {
			registerSingleton {
				TextRepository("parent")
			}
		}
		val child = parent.child().apply {
		}
		val parentRepository: TextRepository = parent.resolveBean()
		val childRepository: TextRepository = child.resolveBean()
		assertEquals(parentRepository, childRepository)
	}
}
