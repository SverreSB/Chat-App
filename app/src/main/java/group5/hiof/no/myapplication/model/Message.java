package group5.hiof.no.myapplication.model;

import java.util.UUID;

public class Message {

    private UUID messageID;
    private User sender;
    private User receiver;
    private String timestamp;
    private String format;
    private String messageContent;

    public Message(User sender, User receiver, String format, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.format = format;
        this.messageContent = content;
    }


    /*
     *  Getters and setters for field variables
     */

    public UUID getMessageID() {
        return messageID;
    }

    public void setMessageID(UUID messageID) {
        this.messageID = messageID;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
