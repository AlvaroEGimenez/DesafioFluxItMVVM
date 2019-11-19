package com.example.desafiofluxitmvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RamdomUserResponse implements Serializable {

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("login")
    @Expose
    private Login login;
    @SerializedName("dob")
    @Expose
    private Dob dob;
    @SerializedName("registered")
    @Expose
    private Registered registered;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("cell")
    @Expose
    private String cell;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("picture")
    @Expose
    private Picture picture;
    @SerializedName("nat")
    @Expose
    private String nat;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public RamdomUserResponse withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public RamdomUserResponse withName(Name name) {
        this.name = name;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RamdomUserResponse withLocation(Location location) {
        this.location = location;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RamdomUserResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public RamdomUserResponse withLogin(Login login) {
        this.login = login;
        return this;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public RamdomUserResponse withDob(Dob dob) {
        this.dob = dob;
        return this;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public RamdomUserResponse withRegistered(Registered registered) {
        this.registered = registered;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RamdomUserResponse withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public RamdomUserResponse withCell(String cell) {
        this.cell = cell;
        return this;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public RamdomUserResponse withId(Id id) {
        this.id = id;
        return this;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public RamdomUserResponse withPicture(Picture picture) {
        this.picture = picture;
        return this;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public RamdomUserResponse withNat(String nat) {
        this.nat = nat;
        return this;
    }
}
