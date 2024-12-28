import {KeycloakConfig} from "./types";
import dotenv from 'dotenv';
import path from 'path';
const __dirname = path.dirname(new URL(import.meta.url).pathname);

dotenv.config({ path: path.resolve(__dirname, '../../../.env') });

export const keycloakConfig: KeycloakConfig = {
    serverUrl: process.env.KEYCLOAK_SERVER_URL || 'http://localhost:8081',
    realm: process.env.KEYCLOAK_REALM || 'master',
    clientId: process.env.KEYCLOAK_CLIENT_ID || 'admin-cli',
    clientSecret: process.env.KEYCLOAK_CLIENT_SECRET || ''
};

export const SWAGGER_URL = process.env.SWAGGER_URL || 'http://localhost:8080/v3/api-docs';