import {keycloakConfig, SWAGGER_URL} from "./config.js";
import {OpenApiResponse} from "./types";
import axios from "axios";
import KcAdminClient from '@keycloak/keycloak-admin-client';
import { v4 as uuidv4 } from 'uuid';

import type ResourceRepresentation from "@keycloak/keycloak-admin-client/lib/defs/resourceRepresentation";
import type {RequestArgs} from "@keycloak/keycloak-admin-client/lib/resources/agent";
import {GrantTypes} from "@keycloak/keycloak-admin-client/lib/utils/auth";
import ClientRepresentation from "@keycloak/keycloak-admin-client/lib/defs/clientRepresentation";

class Seeder {
    kcAdminClient: KcAdminClient;
    applicationClient: ClientRepresentation | null = null;
    applicationResources: ResourceRepresentation[] = [];

     constructor() {
         console.log('Initializing Keycloak Admin Client...');
         this.kcAdminClient = new KcAdminClient({
             baseUrl: keycloakConfig.serverUrl,
             realmName: keycloakConfig.realm,
         });
     }

     async main(){
         await this.authenticate();
         console.log('Fetching Client...');
         await this.getClient();
         await this.getResources();
         await this.process();
     }

     async authenticate(){
         await this.kcAdminClient.auth({
             grantType: "client_credentials",
             clientId: keycloakConfig.clientId,
             clientSecret: keycloakConfig.clientSecret,
         }).then((data) => {
             console.log("Authenticated successfully");
         });
     }

     async getClient(){
          let client = await this.kcAdminClient.clients.find({
             clientId: "portfolio"
         });
          this.applicationClient = client[0];
     }

     async getResources(){
         // security.oauth2.resourceserver.jwt.jwk-set-uri
         if(this.applicationClient == null) return;
         await this.kcAdminClient.clients.listResources({
             id: this.applicationClient.id!
         }).then((data) => {
             this.applicationResources = data;
         }).catch((error) => {
             console.error("Error fetching resources: " + error.message);
         });
     }

     checkIfExist(resourceName: string){
         this.applicationResources.forEach(ele => {
             console.log("Resource found: " + resourceName);
             if(ele.name === resourceName) return true;
         })
         return false;
     }

    extractResource(resourceName: string): ResourceRepresentation | null{
        for(const ele of this.applicationResources){
            if(ele.name === resourceName) {
                console.log("Resource exists: " + resourceName);
                return ele;
            }
        }
        return null;
    }

     async process() {
        try {
            console.log('Fetching Swagger docs...');
            const swaggerResponse = await axios.get<OpenApiResponse>(SWAGGER_URL);
            const swaggerResponseData = swaggerResponse.data;



            console.log('Processing endpoints...');
            let index = 0;
            for (const [path, methods] of Object.entries(swaggerResponseData.paths)) {
                for (const [method, details] of Object.entries(methods)) {
                    let operationId: string = (details.operationId).split("_")[0];
                    let urn: string = ("urn:" + (swaggerResponseData.info.microservice ? swaggerResponseData.info.microservice : "c.p") + ":" + details.tags[0]+ ":" + operationId).toLowerCase().trim().replaceAll("/s" , "-");
                    let resourceName = ((details.summary) ? details.summary : details.tags[0] + "-" + operationId).toLowerCase();
                    // console.log(`Creating resource: Resource: ${resourceName} Path: ${path} Method: ${method.toUpperCase()} URN: ${urn}`);

                    // await this.createResourceInKeycloak(resourceName, path, method.toUpperCase(), urn);
                    await this.deleteResourceInKeycloak(resourceName);
                }
            }

            console.log('All resources created successfully!');
        } catch (error: any) {
            console.error('Error:', error.message);
        }
    }

    async deleteResourceInKeycloak(resourceName: string){
         let resource = this.extractResource(resourceName);
         if(resource == null) return;
        await this.kcAdminClient.clients.delResource({
            id: this.applicationClient?.id!,
            resourceId: resource._id!
        }).then((data) =>{
            console.log("Resource deleted: " + resourceName);
        }).catch((error) =>{
            console.error("Some error while deleting resource");
        });
    }

    async createResourceInKeycloak(resourceName: string, uri: string, scope: string, urn: string) {
        try {
            if(this.checkIfExist(resourceName)) return;

            let payload: ResourceRepresentation = {
                displayName: resourceName,
                name: resourceName,
                uris: [uri],
                type: urn,
                scopes: [{name: scope, displayName: scope}]
            }
            await this.kcAdminClient.clients.createResource({
                id: this.applicationClient?.id!,
                realm: keycloakConfig.realm
            }, payload);
            console.log(`Resource "${resourceName}" created successfully.`);
        } catch (error: any) {
            console.error(`Failed to create resource "${resourceName}":`, error.response?.data || error.message);
        }
    }
}
export default new Seeder();