package com.greentree.commons.tests;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DisabledIfRunInIDECondition implements ExecutionCondition {
	
	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		if(System.getProperty("localRepository") != null) // only on Maven
			return ConditionEvaluationResult.enabled("used Maven");
		
		return ConditionEvaluationResult.disabled("run id IDE");
	}
	
}
