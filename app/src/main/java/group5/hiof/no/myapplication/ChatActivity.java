package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import group5.hiof.no.myapplication.model.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatActivity extends AppCompatActivity {

    private TextView chatPartner;
    private Button exitChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        exitChat = findViewById(R.id.buttonExitChat);
        chatPartner = findViewById(R.id.chatPartner);
        Chat chat = (Chat) getIntent().getSerializableExtra("CHAT");

        if(chat != null) {
            chatPartner.setText(chat.getReceiver() + " " + chat.getUid());
        }
        else {
            chatPartner.setText("NEW");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
        }

        exitChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
