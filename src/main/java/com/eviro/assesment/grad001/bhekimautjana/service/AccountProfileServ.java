package com.eviro.assesment.grad001.bhekimautjana.service;

import com.eviro.assesment.grad001.bhekimautjana.entity.AccountProfile;
import com.eviro.assesment.grad001.bhekimautjana.repository.AccountProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountProfileServ {



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

    public String getAccountProfileHttpImageLink(String name, String surname){
        if(name==null ||name.isEmpty()|| surname==null ||surname.isEmpty()){
            return null;
        }
        return repo.getHttpImageLinkByHolderNameAndHolderSurname(name,surname);
    }


}
