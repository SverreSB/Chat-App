package group5.hiof.no.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import group5.hiof.no.myapplication.R;

public class Chat implements Serializable {

    // NEW CONSTRUCTOR AND VARIABLES FOR TESTING
    private String sender;
    private String receiver;
    private int avatar;
    private int uid;

    public Chat(String sender, String receiver, int avatar, int uid) {
        this.sender = sender;
        this.receiver = receiver;
        this.avatar = avatar;
        this.uid = uid;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getAvatar() {
        return avatar;
    }

    public int getUid() {
        return uid;
    }

    // Create static data for testing
    public static ArrayList<Chat> getChats() {
        ArrayList<Chat> data = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Chat chat = new Chat("Sender", "Receiver", R.drawable.ic_action_profile, i);
            data.add(chat);
        }

        return data;
    }
}
