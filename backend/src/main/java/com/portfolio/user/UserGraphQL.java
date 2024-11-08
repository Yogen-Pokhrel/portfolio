package com.portfolio.user;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

@Component
public class UserGraphQL {

    @QueryMapping("testEndpoint")
    public String testEndpoint() {
        return "This is graphql endpoint";
    }
}
