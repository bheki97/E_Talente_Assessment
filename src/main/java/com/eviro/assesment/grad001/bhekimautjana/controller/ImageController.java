package com.eviro.assesment.grad001.bhekimautjana.controller;

import com.eviro.assesment.grad001.bhekimautjana.entity.AccountProfile;
import com.eviro.assesment.grad001.bhekimautjana.file_parser.FileParser;
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

@RestController
public class ImageController {

    @Autowired
    private FileParseImpl impl;

    @Autowired
    private ResourceLoader resLoader;

    @Autowired
    private AccountProfileServ service;

    @PostMapping("/image")
    public URI getUri(@RequestParam("name") String name, @RequestParam("surname") String surname,
                      @RequestParam("format") String format, @RequestParam("data") String data){
        AccountProfile account = new AccountProfile();
        account.setHolderName(name);
        account.setHolderSurname(surname);
        URI uri = impl.createImageLink(impl.convertCSVDataToImage(data,format));
        account.setHttpImageLink(uri.toString());
        service.addNewAccountProfile(account);


        return uri;
    }
    @GetMapping("/image/{name}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String name) throws IOException {

        FileSystemResource imageResource = new FileSystemResource(
                resLoader.getResource("classpath:static/pictures/"+name).getFile());

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
