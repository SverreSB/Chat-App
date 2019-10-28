package group5.hiof.no.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import group5.hiof.no.myapplication.R;

public class Chat implements Serializable {

    // NEW CONSTRUCTOR AND VARIABLES FOR TESTING
    private String uid;
    private ArrayList<String> participants;
    private int avatar;

    public Chat() {

    }

    public Chat(String sender, String receiver, int avatar) {
        this.avatar = avatar;
        this.participants = new ArrayList<>();
        participants.add(sender);
        participants.add(receiver);
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // Create static data for testing
    public static ArrayList<Chat> getChats() {
        ArrayList<Chat> data = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Chat chat = new Chat("Sender", "Receiver " + i, R.drawable.ic_action_profile);
            data.add(chat);
        }

        return data;
    }

    @Override
    public String toString() {
        return "Participant 1 " + participants.get(0) + "\n" +
                "Participant 2 " + participants.get(1) + "\n" +
                "Avatar " + avatar;
    }
}
