package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import group5.hiof.no.myapplication.model.Chat;

import android.os.Bundle;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    private TextView chatPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatPartner = findViewById(R.id.chatPartner);
        Chat chat = (Chat) getIntent().getSerializableExtra("CHAT");

        chatPartner.setText(chat.getReceiver() + " " + chat.getUid());

    }
}
