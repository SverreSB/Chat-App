package group5.hiof.no.myapplication.model;

import android.location.Location;

import java.util.HashMap;
import java.util.UUID;

public class User {

    private UUID userID;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private Location location;
    private HashMap<UUID, Chat> activeChats;

    public User(String username, String password, Location location) {
        this.username = username;
        this.password = password;
        this.location = location;
        activeChats = new HashMap<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public HashMap<UUID, Chat> getActiveChats() {
        return activeChats;
    }

    public void setActiveChats(HashMap<UUID, Chat> activeChats) {
        this.activeChats = activeChats;
    }

}
