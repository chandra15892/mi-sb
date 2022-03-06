package com.example.gl.azureblob.config;


import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.blob.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

//    @Bean
//    public BlobClientBuilder getClient() {
//    	
//        BlobClientBuilder client = new BlobClientBuilder();
//        client.connectionString(connectionString);
//        client.containerName(containerName);
//        
//        return client;
//    }
    
    
    
    @Bean
    public BlobContainerClient getBlobContainerClient() {
    
    	String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", storageAccount);    	
    	
//    	ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().clientId(clientId).build();  

    	ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().build();  
    	
   	 	BlobServiceClient storageClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(managedIdentityCredential).buildClient();
    	 return storageClient.getBlobContainerClient(containerName);

    }
}