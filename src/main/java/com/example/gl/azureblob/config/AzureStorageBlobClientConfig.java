package com.example.gl.azureblob.config;


import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageBlobClientConfig {

//    @Value("${blob.connection-string}")
//    String connectionString;

    @Value("${blob.container-name}")
    String containerName;
    
    @Value("${mi.client-id}")
    String clientId;
    
    @Value("${blob.storage-account}")
    String storageAccount;

    @Bean
    public BlobClientBuilder getClient() {
    	
//    	  String endpoint = "https://<storageAccount>.blob.core.windows.net";
    	  String endpoint = "https://"+storageAccount+".blob.core.windows.net";

    	ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().clientId(clientId).build();
    	
        BlobClientBuilder client = new BlobClientBuilder();
        client.endpoint(endpoint);
        client.credential(managedIdentityCredential);
//        client.connectionString(connectionString);
        client.containerName(containerName);
        System.out.println(client.toString());
        
        return client;
    }
}