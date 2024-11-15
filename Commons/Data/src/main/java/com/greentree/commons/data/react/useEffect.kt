package com.greentree.commons.data.react

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.react.REACT
import com.greentree.commons.util.react.ReactContext
import com.greentree.commons.util.react.refreshOnEvent

fun ReactContext.refreshOnModify(resource: FileResource) = refreshOnEvent(resource.)
fun refreshOnModify(resource: FileResource) = REACT.get().refreshOnModify(resource)