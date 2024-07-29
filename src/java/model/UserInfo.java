/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

public class UserInfo {
    private int uid;
    private String name, birth, phone, email, address;

    public UserInfo(int uid, String name, String birth, String phone, String email, String address) {
        this.uid = uid;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
