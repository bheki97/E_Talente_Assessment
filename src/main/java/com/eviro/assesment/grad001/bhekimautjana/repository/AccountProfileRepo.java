package com.eviro.assesment.grad001.bhekimautjana.repository;

import com.eviro.assesment.grad001.bhekimautjana.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.URI;

public interface AccountProfileRepo extends JpaRepository<AccountProfile,Integer> {

    boolean existsByHolderNameAndHolderSurname(String holderName,String holderSurname);
    @Query("SELECT a.httpImageLink FROM AccountProfile a WHERE a.holderName = :name AND a.holderSurname = :surname ")
    URI getHttpImageLinkByHolderNameAndHolderSurname(String name, String surname);



}
