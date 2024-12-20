package com.portfolio.apigateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.portfolio.apigateway.helpers.ServiceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@RestController
//@RequestMapping("/openapi")
public class OpenApiController {

//    @Autowired
    ServiceResolver serviceResolver;

    private final RestTemplate restTemplate;

    public OpenApiController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/{service}/v3/api-docs/swagger-config")
    public ResponseEntity<?> getSwaggerConfig(@PathVariable String service) {
        System.out.println("service config: " + service);
        String serviceUrl = serviceResolver.getServiceUrl(service) + "/v3/api-docs/swagger-config";
        System.out.println("service url: " + serviceUrl);
        return restTemplate.getForEntity(serviceUrl + "/" + service, ObjectNode.class);
    }

    @GetMapping("/{service}/v3/api-docs")
    public ResponseEntity<?> getApiDocs(@PathVariable String service) {
        System.out.println("service: " + service);
        String serviceUrl = serviceResolver.getServiceUrl(service) + "/v3/api-docs";
        System.out.println("service url: " + serviceUrl);
        try {
            // Fetch the original OpenAPI JSON from the service
            ResponseEntity<String> response = restTemplate.getForEntity(serviceUrl, String.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch API docs");
            }

            // Parse and modify the OpenAPI JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());

            // Modify the 'servers' array to use the gateway URL
            if (rootNode.has("servers")) {
                ArrayNode servers = (ArrayNode) rootNode.get("servers");
                servers.removeAll(); // Clear existing servers
                ObjectNode gatewayServer = mapper.createObjectNode();
                gatewayServer.put("url", serviceResolver.getPortfolioGatewayUrl());
                servers.add(gatewayServer);
            }

            // Return the modified JSON
            return ResponseEntity.ok(mapper.writeValueAsString(rootNode));

        } catch (Exception e) {
            // Handle errors gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching API docs: " + e.getMessage());
        }
    }
}

