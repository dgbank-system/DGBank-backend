package com.example.DG.bank.system.model.enums;

public enum Gender {
    Male("Male"),
    Female("Female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }
}

