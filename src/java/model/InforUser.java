package model;

import java.util.Date;

public class InforUser {
    private int uID;
    private String name;
    private Date birthdate;
    private String phone;
    private String email;
    private String address;

    public InforUser(int uID, String name, Date birthdate, String phone, String email, String address) {
        this.uID = uID;
        this.name = name;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Getters and setters

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}