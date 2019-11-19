package com.example.desafiofluxitmvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dob {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("age")
    @Expose
    private Long age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Dob withDate(String date) {
        this.date = date;
        return this;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Dob withAge(Long age) {
        this.age = age;
        return this;
    }

}
