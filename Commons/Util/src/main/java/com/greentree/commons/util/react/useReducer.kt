package com.greentree.commons.util.react

typealias Reducer<State, Action> = (state: State, action: Action) -> State
typealias Dispatch<Action> = (action: Action) -> Unit

fun <State, Action> ReactContext.useReducer(
	initial: State,
	reducer: Reducer<State, Action>,
): Dispatch<Action> {
	var s by useState(initial)
	return { action ->
		s = reducer.invoke(s, action)
	}
}

