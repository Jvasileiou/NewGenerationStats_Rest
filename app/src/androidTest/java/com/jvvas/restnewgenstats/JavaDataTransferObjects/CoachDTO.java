package com.jvvas.restnewgenstats.JavaDataTransferObjects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoachDTO implements Serializable {

    @SerializedName("coach_id")
    private String coachId;

    @SerializedName("surname")
    private String surname;

    @SerializedName("name")
    private String name;

    public CoachDTO(String coachId, String surname, String name) {
        this.coachId = coachId;
        this.surname = surname;
        this.name = name;
    }

    public String getCoachId() {
        return coachId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CoachDTO{" +
                "coachId='" + coachId + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
