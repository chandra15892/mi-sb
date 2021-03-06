package com.example.gl.azureblob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.gl.azureblob.service.AzureBlobAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AzureBlobFileController {

	@Autowired
	AzureBlobAdapter azureAdapter;

	@PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Map<String, String> uploadFile(@RequestPart(value = "file", required = true) MultipartFile files) {
		System.out.println("In upload");
		String name = azureAdapter.upload(files, "prefix");
		Map<String, String> result = new HashMap<>();
		result.put("key", name);
		return result;
	}

	@GetMapping(path = "/download")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "file") String file)
			throws IOException {
		System.out.println("In downloadFile");
		byte[] data = azureAdapter.getFile(file);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + file + "\"").body(resource);

	}

	@GetMapping(path = "/test")
	public ResponseEntity<String> testApi() throws IOException {
		System.out.println("In testApi");
		return ResponseEntity.ok().body("Hello World");

	}
	
	@GetMapping(path = "/blob_list")
	public ResponseEntity<List<String>> blobListByContainer()
			throws IOException {
		System.out.println("In blobListByContainer");
		return ResponseEntity.ok().body(azureAdapter.blobListByContainer());

	}
}