package com.example.cms;

public class adminCreateClubModal {
    private String clubName;
    private String clubDescription;
    private String clubCreatedBy;

    public adminCreateClubModal(String clubName, String clubDescription, String clubCreatedBy){
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.clubCreatedBy = clubCreatedBy;
    }

    // ClubName Getter
    public String getClubName(){
        return clubName;
    }
    // ClubName Setter
    public void setClubName(String clubName){
        this.clubName = clubName;
    }
    // ClubDescription Getter
    public String getClubDescription(){
        return clubDescription;
    }
    // ClubDescription Setter
    public void setClubDescription(String clubDescription){
        this.clubDescription = clubDescription;
    }
    // ClubCreatedBy Getter
    public String getClubCreatedBy(){
        return clubCreatedBy;
    }
    // clubCreatedBy Setter
    public void setClubCreatedBy(String clubCreatedBy){
        this.clubCreatedBy = clubCreatedBy;
    }
}