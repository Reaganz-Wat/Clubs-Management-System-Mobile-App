package com.example.cms;

public class student_join_clubs_modal {
    private String clubName, clubDescription, clubCreatedBy;

    public student_join_clubs_modal(String clubName, String clubDescription, String clubCreatedBy) {
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.clubCreatedBy = clubCreatedBy;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubCreatedBy() {
        return clubCreatedBy;
    }

    public void setClubCreatedBy(String clubCreatedBy) {
        this.clubCreatedBy = clubCreatedBy;
    }
}
