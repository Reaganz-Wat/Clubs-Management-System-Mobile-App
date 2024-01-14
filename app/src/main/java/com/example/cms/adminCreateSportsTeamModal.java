package com.example.cms;
public class adminCreateSportsTeamModal {
    String team_name, coach;
    public adminCreateSportsTeamModal(String team_name, String coach) {
        this.team_name = team_name;
        this.coach = coach;
    }

    // getter and setter methods
    public String getTeam_name() {
        return team_name;
    }
    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }
    public String getCoach() {
        return coach;
    }
    public void setCoach(String coach) {
        this.coach = coach;
    }
}
