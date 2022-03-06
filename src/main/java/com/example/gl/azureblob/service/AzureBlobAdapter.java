package com.example.gl.azureblob.service;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobProperties;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AzureBlobAdapter {

//    @Autowired
//    BlobClientBuilder client;
    
    @Autowired
    BlobContainerClient blobContainerClient;




    public String upload(MultipartFile file, String prefixName) {
        if(file != null && file.getSize() > 0) {
            try {
                //implement your own file name logic.
                String fileName = prefixName +file.getOriginalFilename();
                System.out.println("In upload AzureBlobAdapter");
//                client.blobName(fileName).buildClient().upload(file.getInputStream(),file.getSize());
                blobContainerClient.getBlobClient(fileName).upload(file.getInputStream(),file.getSize());
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public byte[] getFile(String name) {
        try {
            File temp = new File(name);
            System.out.println(temp == null);
            System.out.println("In getFile AzureBlobAdapter");
            
//            BlobProperties properties = client.blobName(name).buildClient().downloadToFile(temp.getPath());
            BlobProperties properties = blobContainerClient.getBlobClient(name).downloadToFile(temp.getPath());
            System.out.println(properties.getVersionId());
            byte[] content = Files.readAllBytes(Paths.get(temp.getPath()));
            temp.delete();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteFile(String name) {
        try {
//            client.blobName(name).buildClient().delete();
        	blobContainerClient.getBlobClient(name).delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}