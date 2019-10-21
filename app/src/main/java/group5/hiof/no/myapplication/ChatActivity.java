package group5.hiof.no.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.model.User;
import group5.hiof.no.myapplication.database.ChatDB;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    private TextView chatPartner;
    private EditText messageField;
    private Button exitChat;
    private Button sendMessageButton;

    FirebaseAuth mAuth;
    ChatDB chatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        exitChat = findViewById(R.id.buttonExitChat);
        chatPartner = findViewById(R.id.chatPartner);
        sendMessageButton = findViewById(R.id.buttonSendMessage);
        messageField = findViewById(R.id.inputMessage);

        Chat chat = (Chat) getIntent().getSerializableExtra("CHAT");

        if(chat != null) {
            String sender = mAuth.getCurrentUser().getUid();
            chatPartner.setText("Get Receiver with sender ID");
        }
        else {
            chatPartner.setText("NEW");
            String userId = mAuth.getCurrentUser().getUid();

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
                    final String message = messageField.getText().toString();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference userCollection = db.collection("users");
                    //Fining a random receiver
                    userCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            chatDB = new ChatDB();
                            if(task.isSuccessful()) {
                                ArrayList<String> userList = new ArrayList<>();
                                for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                    String user = documentSnapshot.getId();
                                    userList.add(user);
                                }

                                // Change up logic to handle duplicate chats
                                String randomUser = userList.get(new Random().nextInt(userList.size()));
                                String currentUser = mAuth.getUid();
                                chatDB.createChat(currentUser, randomUser, message);
                            }
                        }
                    });
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
}
