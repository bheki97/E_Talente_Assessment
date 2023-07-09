package com.eviro.assesment.grad001.bhekimautjana.component;

import com.eviro.assesment.grad001.bhekimautjana.service.AccountProfileServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Component
public class ReadCSVFile implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private AccountProfileServ accProfServ;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Properties prop = new Properties();
        File csvfile = null;

        //getting the file path of the properties
        Path resourcePath = Paths.get("src", "main", "resources", "static","properties","csv-config.properties").toAbsolutePath();


        try(FileReader fileReader = new FileReader(resourcePath.toFile())){
            prop.load(fileReader);
            csvfile = new File(prop.getProperty("fileParserPath"));
        }catch(IOException iox){
            iox.printStackTrace();
            return;
        }


        accProfServ.loadAccountProfileFromCSVFile(csvfile);

    }
}
