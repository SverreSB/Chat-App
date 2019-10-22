package group5.hiof.no.myapplication.database;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import group5.hiof.no.myapplication.R;
import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.model.Message;

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
                final String currentUserID = mAuth.getUid();

                if(task.isSuccessful()) {
                    ArrayList<String> userList = new ArrayList<>();
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        String userID = documentSnapshot.getId();
                        if(!userID.equals(currentUserID)) {
                            userList.add(userID);
                        }
                    }

                    final String receiver = userList.get(new Random().nextInt(userList.size()));
                    final String content = message;
                    Chat chat = new Chat(sender, receiver, R.drawable.ic_action_profile);


                    db.collection("chats")
                            .add(chat)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Message message = new Message(currentUserID, receiver, content);
                                    documentReference.collection("messages").add(message);
                                }
                            });
                }
            }
        });
    }
}
