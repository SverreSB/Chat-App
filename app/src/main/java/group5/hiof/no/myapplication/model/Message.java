package group5.hiof.no.myapplication.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {

    private String sender;
    private String receiver;
    private String timestamp;
    private String messageContent;

    public Message() {

    }

    public Message(String sender, String receiver, String messageContent) {
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        this.messageContent = messageContent;
    }

    public Message(String sender, String receiver, String messageContent, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }


    /*
     *  Getters and setters for field variables
     */

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
