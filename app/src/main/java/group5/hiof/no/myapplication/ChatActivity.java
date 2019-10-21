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
import android.widget.Toast;

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
}
