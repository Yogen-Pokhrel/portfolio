package com.portfolio.auth.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeycloakSecurityUtil {
	
	Keycloak keycloak;
	
	@Value("${keycloak.server-url}")
	private String serverUrl;
	
	@Value("${keycloak..realm}")
	private String realm;
	
	@Value("${keycloak.client-id}")
	private String clientId;

	@Value("${keycloak.client-secret}")
	private String clientSecret;
	
	public Keycloak getKeycloakInstance() {
		if(keycloak == null) {
			log.debug("Keycloak instance not set, creating new Keycloak instance");
			keycloak = KeycloakBuilder
					.builder().serverUrl(serverUrl).realm(realm)
					.clientId(clientId).grantType(OAuth2Constants.CLIENT_CREDENTIALS)
					.clientSecret(clientSecret)
					.build();
		}
		return keycloak;
	}
}