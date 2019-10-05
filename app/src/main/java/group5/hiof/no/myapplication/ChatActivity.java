package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import group5.hiof.no.myapplication.model.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        }

        exitChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
