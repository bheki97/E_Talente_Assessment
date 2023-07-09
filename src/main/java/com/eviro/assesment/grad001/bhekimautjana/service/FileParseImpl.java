package com.eviro.assesment.grad001.bhekimautjana.service;

import com.eviro.assesment.grad001.bhekimautjana.file_parser.FileParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
public class FileParseImpl implements FileParser {


    private String[][] csvData;


    public String[][] getCsvData() {
        return csvData;
    }

    @Override
    public void parseCSv(File csvFile) {

        try {
            //read all the lines on the csv File
            List<String> lines = Files.readAllLines(csvFile.toPath());

            //remove the heading
            lines.remove(0);

            //setting the 2-D Array Size
            csvData = new String[lines.size()][] ;
            String[] csv =null;

            //these nested for loop maps the csv file content into the 2-D Array

            //initially starts at line 2 index-1 since the first line is the heading
            for(int i=0;i<lines.size();i++){

                //get the comma separated values of each line
                csvData[i] = lines.get(i).split(",");


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public File convertCSVDataToImage(String base64ImageData,String extension) {
        StringBuilder filepath =new StringBuilder();
        Path resourcePath = Paths.get("src", "main", "resources", "static").toAbsolutePath();

        filepath.append(resourcePath.toString())
                    .append("\\pictures\\")
                    .append(UUID.randomUUID())
                    .append(".")
                    .append(extension);

        File file = new File(filepath.toString());

        //check if file exists
        //in case file creation fails it will return null
        if(!file.exists()){
            try {
                System.out.println(file.getPath());
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error: "+e.getMessage());
                return null;
            }

        }

        //decode the String byte image
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageData);


        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(imageBytes);

        } catch (IOException e) {
            file =null;
        }


        return file;
    }

    @Override
    public URI createImageLink(File fileImage) {
        Properties prop = new Properties();
        Path resourcePath = Paths.get("src", "main", "resources", "static","properties","csv-config.properties").toAbsolutePath();
        String uri = null;

        try(FileReader fileReader = new FileReader(resourcePath.toFile())){
            prop.load(fileReader);
            uri  =prop.getProperty("httpContextPath");
        }catch(IOException iox){
            iox.printStackTrace();
            return null;
        }
        return URI.create(uri+"/v1/api/image/"+fileImage.getName());
    }


}
