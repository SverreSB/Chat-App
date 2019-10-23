package group5.hiof.no.myapplication.database;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import group5.hiof.no.myapplication.R;
import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.model.Message;
import group5.hiof.no.myapplication.model.User;

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

                // Finds every user in DB and puts it into array list, except for current user.
                if(task.isSuccessful()) {
                    ArrayList<String> userList = new ArrayList<>();
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        String userID = documentSnapshot.getId();
                        if(!userID.equals(currentUserID)) {
                            userList.add(userID);
                        }
                    }

                    final String receiver = userList.get(new Random().nextInt(userList.size()));
                    Chat chat = new Chat(sender, receiver, R.drawable.ic_action_profile);

                    // Creates chat collection and adds message collection within chat after creation. Updates user's activeChats
                    db.collection("chats")
                            .add(chat)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Message newMessage = new Message(currentUserID, receiver, message);
                                    documentReference.collection("messages").add(newMessage);

                                    DocumentReference currentUserDocument = db.collection("users").document(mAuth.getUid());
                                    DocumentReference receiverUserDocument = db.collection("users").document(receiver);
                                    currentUserDocument.update("activeChats", FieldValue.arrayUnion(documentReference.getId()));
                                    receiverUserDocument.update("activeChats", FieldValue.arrayUnion(documentReference.getId()));

                                }
                            });
                }
            }
        });
    }

}
