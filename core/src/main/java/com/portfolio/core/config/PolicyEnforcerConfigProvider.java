package com.portfolio.core.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PolicyEnforcerConfigProvider {

    private static PolicyEnforcerConfig policyEnforcerConfig;

    private PolicyEnforcerConfigProvider() {}

    public static PolicyEnforcerConfig getPolicyEnforcerConfig(KeycloakConfig keycloakConfig) {
        if (policyEnforcerConfig == null) {
            try {
                log.debug("Loading policy enforcer config");
                policyEnforcerConfig = JsonSerialization.
                        readValue(PolicyEnforcerConfigProvider.class.getResourceAsStream("/policy-enforcer.json"),
                                PolicyEnforcerConfig.class);
                policyEnforcerConfig.setRealm(keycloakConfig.getRealm());
                policyEnforcerConfig.setAuthServerUrl(keycloakConfig.getServerUrl());
                policyEnforcerConfig.setResource(keycloakConfig.getClientId());
                Map<String, Object> credentials = new HashMap<>();
                credentials.put("secret", keycloakConfig.getClientSecret());
                policyEnforcerConfig.setCredentials(credentials);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return policyEnforcerConfig;
    }
}
