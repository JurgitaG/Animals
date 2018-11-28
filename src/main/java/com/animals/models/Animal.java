package com.animals.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Animal {
    @Id
    private String _id;
    private String ownerId;
    private String name;
    private String num;
    private Date birthDate;

    public Animal() {

    }
    public Animal(String name, String num, Date birthDate) {
        this.name = name;
        this.num = num;
        this.birthDate = birthDate;
    }


    public String getId() {
        return _id;
    }
    public void setId(String id){
        this._id = id;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getNum(){
        return num;
    }
    public void setNum(String num){
        this.num = num;
    }
    public Date getBirthDate(){
        return birthDate;
    }
    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }
}