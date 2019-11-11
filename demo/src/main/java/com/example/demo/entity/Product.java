package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import javax.persistence.*;

@Entity
@Table( name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    @Column(name = "job_title", length = 64, nullable = false)
    private String jobTitle;

    private String address;
    private String telephone;
    private String email;
    private String website;
    private String language;
    private String about;

    @Column(name = "work_experience", nullable = false)
    private String workExperience;
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AppUser appUser;

    public Product(){}

    public Product(Long productId, String name, String jobTitle, Boolean enabled, AppUser appUser) {
        this.productId = productId;
        this.name = name;
        this.jobTitle = jobTitle;
        this.enabled = enabled;
        this.appUser = appUser;
    }

    public Product(String name, String jobTitle, String address, String telephone, String email,
                   String website, String language, String about) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.website = website;
        this.language = language;
        this.about = about;
    }

    public Product(String name, String jobTitle, String address, String telephone, String email,
                   String website, String language, String about, String workExperience) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.website = website;
        this.language = language;
        this.about = about;
        this.workExperience = workExperience;
    }

    public Product(Long productId, String name, String jobTitle, String address, String telephone, String email,
                   String website, String language, String about, boolean enabled) {
        this.productId = productId;
        this.name = name;
        this.jobTitle = jobTitle;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.website = website;
        this.language = language;
        this.about = about;
        this.enabled = enabled;
    }

    public Product(Long productId, String name, String jobTitle, String address, String telephone, String email,
                   String website, String language, String about, String workExperience, boolean enabled) {
        this.productId = productId;
        this.name = name;
        this.jobTitle = jobTitle;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.website = website;
        this.language = language;
        this.about = about;
        this.workExperience = workExperience;
        this.enabled = enabled;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toString(Product product) {
        return new Gson().toJson(product);
    }


}