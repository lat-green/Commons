package com.greentree.commons.tests.aop

tailrec fun gcd(a: Int, b: Int): Int =
	if(b == 0)
		a
	else
		gcd(b, a % b)
