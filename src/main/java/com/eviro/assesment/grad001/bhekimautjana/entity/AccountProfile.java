package com.eviro.assesment.grad001.bhekimautjana.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class AccountProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String holderName;
    private String holderSurname;
    private String httpImageLink;

    public AccountProfile() {
    }

    public AccountProfile(String holderName, String holderSurname, String httpImageLink) {
        this.holderName = holderName;
        this.holderSurname = holderSurname;
        this.httpImageLink = httpImageLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getHolderSurname() {
        return holderSurname;
    }

    public void setHolderSurname(String holderSurname) {
        this.holderSurname = holderSurname;
    }

    public String getHttpImageLink() {
        return httpImageLink;
    }

    public void setHttpImageLink(String httpImageLink) {
        this.httpImageLink = httpImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountProfile that = (AccountProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(holderName, that.holderName) && Objects.equals(holderSurname, that.holderSurname) && Objects.equals(httpImageLink, that.httpImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holderName, holderSurname, httpImageLink);
    }

    @Override
    public String toString() {
        return "AccountProfile{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", holderSurname='" + holderSurname + '\'' +
                ", httpImageLink='" + httpImageLink + '\'' +
                '}';
    }
}
