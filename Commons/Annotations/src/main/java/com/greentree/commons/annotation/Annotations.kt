package com.greentree.commons.annotation

object Annotations : AnnotationFilter.Chain by AliasForFilter.toChain(AnnotationInheritedFilter.toChain())