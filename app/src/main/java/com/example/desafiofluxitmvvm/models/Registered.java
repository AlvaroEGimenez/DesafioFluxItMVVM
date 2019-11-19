package com.example.desafiofluxitmvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registered {

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

    public Registered withDate(String date) {
        this.date = date;
        return this;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Registered withAge(Long age) {
        this.age = age;
        return this;
    }
}
