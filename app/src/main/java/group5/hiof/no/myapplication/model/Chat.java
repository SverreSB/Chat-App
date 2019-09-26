package group5.hiof.no.myapplication.model;

import java.util.ArrayList;
import java.util.UUID;

public class Chat {

    private UUID chatID;
    private User sender;
    private User receiver;
    private ArrayList<Message> conversation;

    public Chat(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        conversation = new ArrayList<>();
    }


    /*
     * Getters and setters for field variables
     */

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
}
