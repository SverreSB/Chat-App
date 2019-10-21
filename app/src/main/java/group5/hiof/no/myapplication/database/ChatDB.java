package group5.hiof.no.myapplication.database;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import group5.hiof.no.myapplication.R;
import group5.hiof.no.myapplication.model.Chat;

public class ChatDB {
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public ChatDB() {

    }

    public void createChat(final String sender, final String message) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        CollectionReference userCollection = db.collection("users");

        //Fining a random receiver before creating a chat document in chats collection
        userCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String currentUserID = mAuth.getUid();

                if(task.isSuccessful()) {
                    ArrayList<String> userList = new ArrayList<>();
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        String userID = documentSnapshot.getId();
                        if(!userID.equals(currentUserID)) {
                            userList.add(userID);
                        }
                    }

                    String receiver = userList.get(new Random().nextInt(userList.size()));
                    Chat chat = new Chat(sender, receiver, R.drawable.ic_action_profile);
                    ArrayList<String> messages = new ArrayList<>();
                    messages.add(message);
                    chat.setMessages(messages);

                    db.collection("chats").add(chat);
                }
            }
        });
    }
}
