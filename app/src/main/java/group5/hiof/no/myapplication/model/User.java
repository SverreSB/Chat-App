package group5.hiof.no.myapplication.model;

import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private String avatar;
    private double latitude;
    private double longitude;
    private ArrayList<String> activeChats;

    public User() {
        //Empty constructor
    }

    public User(String username, String email, String avatar, double latitude, double longitude) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.latitude = latitude;
        this.longitude = longitude;

        this.activeChats = new ArrayList<>();
    }

    /*
     *  Getters and setters for field variables.
     */


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getActiveChats() {
        return activeChats;
    }

    public void setActiveChats(ArrayList<String> activeChats) {
        this.activeChats = activeChats;
    }

    public void addChat(String id) {
        this.activeChats.add(id);
    }

}
