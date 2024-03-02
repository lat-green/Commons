package com.greentree.commons.tests;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DisabledIfInternetNotConnectedCondition implements ExecutionCondition {

    private static final boolean netIsAvailable = netIsAvailable();

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (netIsAvailable)
            return ConditionEvaluationResult.enabled("Internet is available");
        return ConditionEvaluationResult.disabled("Internet is not available");
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("https://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.setConnectTimeout(1000);
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

}
