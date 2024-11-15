package com.greentree.commons.data.react

import com.greentree.commons.action.react.refreshOnEvent
import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.util.react.REACT
import com.greentree.commons.util.react.ReactContext

fun ReactContext.refreshOnModify(resource: FileResource) = refreshOnEvent(resource.onModify)
fun refreshOnModify(resource: FileResource) = REACT.get().refreshOnModify(resource)