package com.greentree.commons.util

inline fun repeat(times: Long, action: (Long) -> Unit) {
	for(index in 0L until times) {
		action(index)
	}
}