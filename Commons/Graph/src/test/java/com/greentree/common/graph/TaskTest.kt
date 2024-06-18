package com.greentree.common.graph

import com.greentree.commons.graph.DirectedGraph
import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.RootTree
import com.greentree.commons.graph.RootTreeBase
import com.greentree.commons.graph.algorithm.path.AbstractTreeVistor
import com.greentree.commons.graph.algorithm.path.min.MinPathFinder
import com.greentree.commons.graph.algorithm.walk.BFSWalker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*
import java.util.List
import kotlin.math.min

@Disabled
class TaskTest {
	private class State(val v1: Int, val v2: Int) {

		override fun hashCode(): Int {
			return Objects.hash(v1, v2)
		}

		override fun equals(obj: Any?): Boolean {
			if(this === obj) return true
			if(obj !is State) return false
			return v1 == obj.v1 && v2 == obj.v2
		}

		override fun toString(): String {
			return "State [$v1, $v2]"
		}
	}

	private class StateFiniteGraph : FiniteGraph<State> {

		override fun iterator(): Iterator<State> {
			throw UnsupportedOperationException("get vertex of infinity graph")
		}

		override fun getAdjacencySequence(v: State) = sequence {
			val s = v
			yield(State(5, s.v2))
			yield(State(0, s.v2))
			yield(State(s.v1, 3))
			yield(State(s.v1, 0))
			val x3 = min((s.v1 + s.v2).toDouble(), 3.0).toInt()
			yield(State(s.v1 + s.v2 - x3, x3))
			val x5 = min((s.v1 + s.v2).toDouble(), 5.0).toInt()
			yield(State(x5, s.v1 + s.v2 - x5))
		}

		companion object {

			private const val serialVersionUID = 1L
		}
	}

	@Test
	fun test1() {
		val graph = StateFiniteGraph()
		val start = State(0, 0)
		val finish = State(4, 0)
		val finder = MinPathFinder(
			BFSWalker<State>(
				graph
			)
		)
		val solve = finder.getPath(start, finish)
		Assertions.assertEquals(
			solve, List.of(
				State(0, 0), State(5, 0), State(2, 3), State(2, 0), State(0, 2),
				State(5, 2), State(4, 3), State(4, 0)
			)
		)
	}

	@Test
	fun test2() {
		val g = DirectedGraph<String>()
		g.add("A", "B")
		val v = object : AbstractTreeVistor<String>() {
			var max: RootTree<String>? = null

			override fun add(tree: RootTree<String>): Boolean {
				if((max == null) || (max!!.size < tree.size)) max = tree
				return true
			}
		}
		g.walker().visit(v)
		val t1 = RootTreeBase("A")
		t1.add("B", "A")
		Assertions.assertEquals(v.max, t1)
	}
}
