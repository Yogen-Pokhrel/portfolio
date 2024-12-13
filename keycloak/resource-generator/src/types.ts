export interface KeycloakConfig {
    serverUrl: string;
    realm: string;
    clientId: string;
    clientSecret: string
}

export interface OpenApiResponse {
    openapi: string,
    info: {
        "title": string,
        "description": string,
        "version": string,
        "microservice"?: string
    }
    paths:{
        [key: string]:{
            [httpMethod: string]: {
                "summary": string,
                "description": string,
                "operationId": string,
                "tags":string[]
            }
        }
    }
}