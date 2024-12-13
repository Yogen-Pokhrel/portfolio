import { keycloakConfig, SWAGGER_URL } from "./config.js";
import axios from "axios";
import KcAdminClient from '@keycloak/keycloak-admin-client';
class Seeder {
    constructor() {
        this.applicationClient = null;
        this.applicationResources = [];
        console.log('Initializing Keycloak Admin Client...');
        this.kcAdminClient = new KcAdminClient({
            baseUrl: keycloakConfig.serverUrl,
            realmName: keycloakConfig.realm,
        });
    }
    async main() {
        await this.authenticate();
        console.log('Fetching Client...');
        await this.getClient();
        await this.getResources();
        await this.process();
    }
    async authenticate() {
        await this.kcAdminClient.auth({
            grantType: "client_credentials",
            clientId: keycloakConfig.clientId,
            clientSecret: keycloakConfig.clientSecret,
        }).then((data) => {
            console.log("Authenticated successfully");
        });
    }
    async getClient() {
        let client = await this.kcAdminClient.clients.find({
            clientId: "portfolio"
        });
        this.applicationClient = client[0];
    }
    async getResources() {
        if (this.applicationClient == null)
            return;
        await this.kcAdminClient.clients.listResources({
            id: this.applicationClient.id
        }).then((data) => {
            this.applicationResources = data;
        }).catch((error) => {
            console.error("Error fetching resources: " + error.message);
        });
    }
    checkIfExist(resourceName) {
        this.applicationResources.forEach(ele => {
            if (ele.name === resourceName)
                return true;
        });
        return false;
    }
    async process() {
        try {
            console.log('Fetching Swagger docs...');
            const swaggerResponse = await axios.get(SWAGGER_URL);
            const swaggerResponseData = swaggerResponse.data;
            console.log('Processing endpoints...');
            let index = 0;
            for (const [path, methods] of Object.entries(swaggerResponseData.paths)) {
                for (const [method, details] of Object.entries(methods)) {
                    let operationId = (details.operationId).split("_")[0];
                    let urn = ("urn:" + (swaggerResponseData.info.microservice ? swaggerResponseData.info.microservice : "c.p") + ":" + details.tags[0] + ":" + operationId).toLowerCase().trim().replaceAll("/s", "-");
                    let resourceName = ((details.summary) ? details.summary : details.tags[0] + "-" + operationId).toLowerCase();
                    console.log(`Creating resource: Resource: ${resourceName} Path: ${path} Method: ${method.toUpperCase()} URN: ${urn}`);
                    await this.createResourceInKeycloak(resourceName, path, method.toUpperCase(), urn);
                }
            }
            console.log('All resources created successfully!');
        }
        catch (error) {
            console.error('Error:', error.message);
        }
    }
    async createResourceInKeycloak(resourceName, uri, scope, urn) {
        try {
            if (this.checkIfExist(resourceName))
                return;
            let payload = {
                displayName: resourceName,
                name: resourceName,
                uris: [uri],
                type: urn,
                scopes: [{ name: scope, displayName: scope }]
            };
            await this.kcAdminClient.clients.createResource({
                id: this.applicationClient?.id,
                realm: keycloakConfig.realm
            }, payload);
            console.log(`Resource "${resourceName}" created successfully.`);
        }
        catch (error) {
            console.error(`Failed to create resource "${resourceName}":`, error.response?.data || error.message);
        }
    }
}
export default new Seeder();
