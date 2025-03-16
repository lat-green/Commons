package com.greentree.commons.data.react

import com.greentree.commons.action.react.refreshOnEvent
import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.util.react.ReactContext
import com.greentree.commons.util.react.useMemo

fun ReactContext.refreshOnModify(resource: FileResource) =
	refreshOnEvent(useMemo(resource) { resource.onModify })