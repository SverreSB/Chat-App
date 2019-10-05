package group5.hiof.no.myapplication.model;

import java.util.ArrayList;
import java.util.UUID;

import group5.hiof.no.myapplication.R;

public class Chat {

    /*
    private UUID chatID;
    private User sender;
    private User receiver;
    private ArrayList<Message> conversation;

    public Chat(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        conversation = new ArrayList<>();

    }
    */

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

    /*
     *  Getters and setters for field variables
     */

    /*
    public UUID getChatID() {
        return chatID;
    }

    public void setChatID(UUID chatID) {
        this.chatID = chatID;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public ArrayList<Message> getConversation() {
        return conversation;
    }

    public void setConversation(ArrayList<Message> conversation) {
        this.conversation = conversation;
    }
    */

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
