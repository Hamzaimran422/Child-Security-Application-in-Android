package com.example.childsecuritysystem;

public class Entities {

}
class login
{
    public String U_Email;
    public String Password;

}
class User
{
    public int U_ID;
    public String U_Name;
    public String U_Email;
    public String Password;
    public String Role;
    public String C_Phone_No;
}
class ShowFenceList
{
    public int F_ID;
    public String F_Name ,FenceStatus ;
    public String Gender;
}

class Signup
{
    public String Role;
    public int U_ID;
    public String U_Name;
    public String U_Email;
    public String Password;
    public String C_Phone_No;
    public String Gender;

}
class AddChild
{
    public String Role;
    public String U_Name;
    public String Password;
    public String C_Phone_No;
}

class Point{
    public int F_ID,P_ID;
    public double Latitude,Longitude;
}

class DeleteChild
{
    public String U_Name;
}