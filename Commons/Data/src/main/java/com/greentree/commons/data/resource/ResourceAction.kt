package com.greentree.commons.data.resource

import com.greentree.commons.action.observable.RunObservable
import java.io.Serializable

interface ResourceAction : Serializable {

	val onCreate: RunObservable
	val onDelete: RunObservable
	val onModify: RunObservable

	object Null : ResourceAction {

		override val onCreate: RunObservable
			get() = RunObservable.NULL
		override val onDelete: RunObservable
			get() = RunObservable.NULL
		override val onModify: RunObservable
			get() = RunObservable.NULL
	}
}