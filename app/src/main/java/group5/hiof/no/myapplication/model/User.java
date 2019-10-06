package group5.hiof.no.myapplication.model;

import java.util.HashMap;
import java.util.UUID;

import group5.hiof.no.myapplication.database.UserDB;

public class User {

    private UUID userID;
    private String username;
    private String email;
    private String avatar;
    private double lat;
    private double lng;
    private HashMap<UUID, Chat> activeChats = new HashMap<>();

    private UserDB userdb = new UserDB();

    public User(String email, double lat, double lng, String id) {
        this.username = "DEFAULT USERNAME";
        this.email = email;
        this.lat = lat;
        this.lng = lng;

        userdb.writeUserToDB(username, email, lat, lng, id);
    }

    /*
     *  Getters and setters for field variables.
     */

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public HashMap<UUID, Chat> getActiveChats() {
        return activeChats;
    }

    public void setActiveChats(HashMap<UUID, Chat> activeChats) {
        this.activeChats = activeChats;
    }

}
