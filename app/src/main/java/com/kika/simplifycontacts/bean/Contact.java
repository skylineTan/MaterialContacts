package com.kika.simplifycontacts.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class Contact implements Comparable<Contact>, Parcelable {

    private String id;
    private String name;
    private String avatarUrl;
    private String phoneNumber;
    private String email;
    private String nickName;
    private String address;
    private String pinyin;
    private char firstChar;


    public Contact(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public Contact() {

    }

    public Contact(String id){
        this.id = id;
    }


    protected Contact(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        nickName = in.readString();
        address = in.readString();
        pinyin = in.readString();
    }


    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }


        @Override public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }


    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getNickName() {
        return nickName;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getPinyin() {
        return pinyin;
    }


    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
        String first = "未取名";
        if(pinyin.length() != 0){
            first = pinyin.substring(0, 1);
        }
        if (first.matches("[A-Za-z]")) {
            firstChar = first.toUpperCase().charAt(0);
        }
        else {
            firstChar = '#';
        }
    }


    public char getFirstChar() {
        return firstChar;
    }


    public void setFirstChar(char firstChar) {
        this.firstChar = firstChar;
    }


    @Override public int compareTo(Contact another) {
        return this.pinyin.compareTo(another.getPinyin());
    }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (firstChar != contact.firstChar) return false;
        if (!id.equals(contact.id)) return false;
        if (!name.equals(contact.name)) return false;
        if (avatarUrl != null
            ? !avatarUrl.equals(contact.avatarUrl)
            : contact.avatarUrl != null) {
            return false;
        }
        if (phoneNumber != null
            ? !phoneNumber.equals(contact.phoneNumber)
            : contact.phoneNumber != null) {
            return false;
        }
        if (email != null
            ? !email.equals(contact.email)
            : contact.email != null) {
            return false;
        }
        if (nickName != null
            ? !nickName.equals(contact.nickName)
            : contact.nickName != null) {
            return false;
        }
        if (address != null
            ? !address.equals(contact.address)
            : contact.address != null) {
            return false;
        }
        return pinyin != null
               ? pinyin.equals(contact.pinyin)
               : contact.pinyin == null;
    }


    @Override public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result +
                (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (pinyin != null ? pinyin.hashCode() : 0);
        result = 31 * result + (int) firstChar;
        return result;
    }


    @Override public int describeContents() {
        return 0;
    }


    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(nickName);
        dest.writeString(address);
        dest.writeString(pinyin);
    }
}
