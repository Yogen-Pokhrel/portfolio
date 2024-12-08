package com.portfolio.core.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;

@Slf4j
public class PolicyEnforcerConfigProvider {
    private static PolicyEnforcerConfig policyEnforcerConfig;
    private PolicyEnforcerConfigProvider() {}

    public static PolicyEnforcerConfig getPolicyEnforcerConfig() {
        if (policyEnforcerConfig == null) {
            try {
                log.debug("Loading policy enforcer config");
                policyEnforcerConfig = JsonSerialization.
                        readValue(PolicyEnforcerConfigProvider.class.getResourceAsStream("/policy-enforcer.json"),
                                PolicyEnforcerConfig.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return policyEnforcerConfig;
    }
}
