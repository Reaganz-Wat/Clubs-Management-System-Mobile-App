package com.example.cms;

public class Practice {
    private String student_name;
    private String student_email;
    private int student_image;

    public Practice(String student_name, String student_email, int student_image){
        this.student_name = student_name;
        this.student_email = student_email;
        this.student_image = student_image;
    }

    // Getter and Setter
    public String getStudent_name(){
        return student_name;
    }
    public void setStudent_name(String student_name){
        this.student_name = student_name;
    }
    public String getStudent_email(){
        return student_email;
    }
    public void setStudent_email(String student_email){
        this.student_email = student_email;
    }
    public int getStudent_image(){
        return student_image;
    }
    public void setStudent_image(int student_image){
        this.student_image = student_image;
    }
}