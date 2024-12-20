package com.portfolio.core.keycloak;

import com.portfolio.core.config.KeycloakConfig;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeycloakAdminClient {
	
	Keycloak keycloak;
	
	KeycloakConfig keycloakConfig;

	@Autowired
	public KeycloakAdminClient(KeycloakConfig keycloakConfig){
		this.keycloakConfig = keycloakConfig;
	}
	
	public Keycloak getInstance() {
		if(keycloak == null) {
			log.debug("Keycloak instance not set, creating new Keycloak instance");
			keycloak = KeycloakBuilder
					.builder().serverUrl(keycloakConfig.getServerUrl()).realm(keycloakConfig.getRealm())
					.clientId(keycloakConfig.getClientId()).grantType(OAuth2Constants.CLIENT_CREDENTIALS)
					.clientSecret(keycloakConfig.getClientSecret())
					.build();
		}
		return keycloak;
	}
}