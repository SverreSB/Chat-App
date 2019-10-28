package group5.hiof.no.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import group5.hiof.no.myapplication.adapter.MessageRecyclerAdapter;
import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.database.ChatDB;
import group5.hiof.no.myapplication.model.Message;
import group5.hiof.no.myapplication.model.User;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private final String TAG = "CHAT_ACTIVITY";
    private TextView chatPartner;
    private EditText messageField;
    private Button exitChat;
    private Button sendMessageButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private ChatDB chatDB;
    private MessageRecyclerAdapter messageRecyclerAdapter;
    private RecyclerView recyclerView;

    private List<Message> messageList;
    private List<String> messageUidList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        exitChat = findViewById(R.id.buttonExitChat);
        chatPartner = findViewById(R.id.chatPartner);
        sendMessageButton = findViewById(R.id.buttonSendMessage);
        messageField = findViewById(R.id.inputMessage);

        // Setting up recycler view for messages
        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        db = FirebaseFirestore.getInstance();

        final Chat chat = (Chat) getIntent().getSerializableExtra("CHAT");

        if(chat != null) {
            String sender = mAuth.getCurrentUser().getUid();
            setHeaderText(sender, chat);
            getChatMessages(chat);

            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMessage(chat);
                }
            });


        }
        else {
            chatPartner.setText("NEW");
            final String userId = mAuth.getCurrentUser().getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String mail = documentSnapshot.getString("email");
                    chatPartner.setText(mail);
                }
            });

            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chatDB = new ChatDB();
                    String message = messageField.getText().toString();
                    chatDB.createChat(userId, message);
                }
            });


        }

        exitChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setHeaderText(String sender, Chat chat) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for(String participants : chat.getParticipants()) {
            if(!participants.equals(sender)) {
                db.collection("users")
                        .document(participants)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    User user = task.getResult().toObject(User.class);
                                    chatPartner.setText(user.getEmail());
                                }
                            }
                        });
            }
        }

    }

    private void getChatMessages(Chat chat) {
        messageList = new ArrayList<>();
        messageUidList = new ArrayList<>();
        CollectionReference chatMessageReference = db.collection("chats").document(chat.getUid()).collection("messages");
        chatMessageReference.orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Error fetching chat massages " + e);
                }

                for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    QueryDocumentSnapshot documentSnapshot = documentChange.getDocument();
                    Message message = documentSnapshot.toObject(Message.class);
                    String messageUid = documentSnapshot.getId();
                    message.setUid(messageUid);
                    if(!messageUidList.contains(messageUid)) {
                        messageUidList.add(messageUid);
                        messageList.add(message);
                    }
                }

                messageRecyclerAdapter = new MessageRecyclerAdapter(ChatActivity.this, messageList);
                recyclerView.setAdapter(messageRecyclerAdapter);

            }
        });
    }

    private void sendMessage(Chat chat) {
        CollectionReference chatMessageReference = db.collection("chats").document(chat.getUid()).collection("messages");

        String sender = mAuth.getUid();
        String receiver = "";
        for(String participants : chat.getParticipants()) {
            if(!participants.equals(sender)) {
                receiver = participants;
            }
        }
        //Message message = new Message(sender, receiver, FieldValue.serverTimestamp(), messageField.getText().toString());
        Map<String, Object> message = new HashMap<>();
        message.put("sender", sender);
        message.put("receiver", receiver);
        message.put("timestamp", FieldValue.serverTimestamp());
        message.put("messageContent", messageField.getText().toString());
        chatMessageReference.add(message);
    }
}
