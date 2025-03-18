package com.greentree.commons.context.environment

interface EnvironmentProvider : Map<String, String> {

	val name: String
}