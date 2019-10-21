package group5.hiof.no.myapplication.database;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import group5.hiof.no.myapplication.R;
import group5.hiof.no.myapplication.model.Chat;

public class ChatDB {
    public ChatDB() {

    }

    public void createChat(String sender, String receiver, String message) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Chat chat = new Chat(sender, receiver, R.drawable.ic_action_profile);
        ArrayList<String> messages = new ArrayList<>();
        messages.add(message);
        chat.setMessages(messages);

        db.collection("chats").add(chat);
    }
}
