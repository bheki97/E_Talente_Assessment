package com.eviro.assesment.grad001.bhekimautjana.service;

import com.eviro.assesment.grad001.bhekimautjana.entity.AccountProfile;
import com.eviro.assesment.grad001.bhekimautjana.repository.AccountProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountProfileServ {



    @Autowired
    private FileParseImpl fileParser;

    @Autowired
    private AccountProfileRepo repo;

    public AccountProfile addNewAccountProfile(AccountProfile profile) {

        //checks for null, preventing null pointer exception
        //keeps unique entry since the image URI is retrieved by name and surname
        if(profile==null ||repo.existsByHolderNameAndHolderSurname(
                profile.getHolderName(),profile.getHolderSurname())){

            return null; //returns the false value since there exists an AccountProfile with the same name and surname
        }

        return repo.save(profile);
    }

    public URI getAccountProfileHttpImageLink(String name, String surname){

        //check for null, preventing null pointer exception
        if(name==null ||name.isEmpty()|| surname==null ||surname.isEmpty()){
            return null;
        }

        return repo.getHttpImageLinkByHolderNameAndHolderSurname(name,surname);
    }

    public void loadAccountProfileFromCSVFile(File file){

        if(file==null|| !file.exists())return;


        fileParser.parseCSv(file);
        List<AccountProfile> accProfiles = new ArrayList<>();
        String[][] data = fileParser.getCsvData();
        AccountProfile profile;
        String imageType;
        String[] tokens;
        File fileImage;
        for(int i=0;i< data.length;i++){
            if(data[i].length==4){
                profile = new AccountProfile();

                //reading the accountHolder name
                profile.setHolderName(data[i][0]);

                //reading the accountHolder Surname
                profile.setHolderSurname(data[i][1]);

                //reading the image format
                tokens = data[i][2].split("/");

                //validating the format since the format is in 2 segments
                if(tokens.length!=2)continue;

                //read image data and store as file
                fileImage = fileParser.convertCSVDataToImage(data[i][3],tokens[1]);

                //generate the httpLinkfor geting the image
                profile.setHttpImageLink(fileParser.createImageLink(fileImage));

                addNewAccountProfile(profile);

            }

        }


    }


}
