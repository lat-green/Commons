package com.greentree.commons.util.react

typealias Reducer<State, Action> = (state: State, action: Action) -> State
typealias Dispatch<Action> = (action: Action) -> Unit

fun <State, Action> ReactContext.useReducer(
	initial: State,
	reducer: Reducer<State, Action>,
): Pair<State, Dispatch<Action>> {
	var state by useState(initial)
	return state to { action ->
		state = reducer(state, action)
	}
}

fun <State, Action> ReactContext.useReducer(
	initial: () -> State,
	reducer: Reducer<State, Action>,
): Pair<State, Dispatch<Action>> {
	var state by useState(initial)
	return state to { action ->
		state = reducer(state, action)
	}
}

