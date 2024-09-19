package com.jvvas.restnewgenstats.JavaDataTransferObjects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefereeDTO implements Serializable {

    @SerializedName("referee_id")
    private String refereeId;

    @SerializedName("surname")
    private String surname;

    @SerializedName("name")
    private String name;

    @SerializedName("organization")
    private String organization;

    public RefereeDTO(String refereeId, String surname, String name, String organization) {
        this.refereeId = refereeId;
        this.surname = surname;
        this.name = name;
        this.organization = organization;
    }

    public RefereeDTO(String surname, String name, String organization) {
        this.surname = surname;
        this.name = name;
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "RefereeDTO{" +
                "refereeId='" + refereeId + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }
}
