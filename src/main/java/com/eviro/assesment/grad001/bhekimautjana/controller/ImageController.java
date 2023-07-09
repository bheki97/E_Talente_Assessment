package com.eviro.assesment.grad001.bhekimautjana.controller;

import com.eviro.assesment.grad001.bhekimautjana.service.AccountProfileServ;
import com.eviro.assesment.grad001.bhekimautjana.service.FileParseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

@RestController
@RequestMapping("/v1/api/image")
public class ImageController {

    @Autowired
    private FileParseImpl impl;

    @Autowired
    private ResourceLoader resLoader;

    @Autowired
    private AccountProfileServ service;

    @GetMapping("/{name}/{surname}")
    public URI getUri(@PathVariable("name") String name, @PathVariable("surname") String surname){

        return service.getAccountProfileHttpImageLink(name,surname);
    }
    @GetMapping("/{file}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String file) throws IOException {

        File fle  = Paths.get("src","main","resources","static","pictures",file).toFile();
        FileSystemResource imageResource = new FileSystemResource(fle);

        if (imageResource.exists()) {
            String filename = imageResource.getFilename();

            //generate media type based on the file type
            String mediaType = "image/"+filename.substring(filename.lastIndexOf(".")+1);

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(mediaType))
                    .body(imageResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
