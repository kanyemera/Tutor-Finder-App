package com.example.tutorfinderapp;

public class Tutors {

    public String fullname, phonenumber, email, address, subjects, priceperhour, tutorphoto;

    public Tutors(){

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getPriceperhour() {
        return priceperhour;
    }

    public void setPriceperhour(String priceperhour) {
        this.priceperhour = priceperhour;
    }

    public String getTutorphoto() {
        return tutorphoto;
    }

    public void setTutorphoto(String tutorphoto) {
        this.tutorphoto = tutorphoto;
    }

    public Tutors(String fullname, String phonenumber, String email, String address, String subjects, String priceperhour, String tutorphoto) {
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
        this.subjects = subjects;
        this.priceperhour = priceperhour;
        this.tutorphoto=tutorphoto;
    }
}
