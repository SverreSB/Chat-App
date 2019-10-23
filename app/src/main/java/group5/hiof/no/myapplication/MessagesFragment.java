package group5.hiof.no.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import group5.hiof.no.myapplication.adapter.ChatRecyclerAdapter;
import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.model.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private ArrayList<Chat> chatList;
    private RecyclerView chatRecyclerView;
    private Button newMessage;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        chatList = Chat.getChats();

        /*db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //Getting chats from current users list of chats and finds the chats by id
        db.collection("users")
                .document(mAuth.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);

                    }
                });*/

        // Initialize and set onClick for the 'new message' button
        newMessage = view.findViewById(R.id.buttonNewMessage);
        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullChat = new Intent(getContext(), ChatActivity.class);
                Chat temp = null;
                fullChat.putExtra("CHAT", temp);
                startActivity(fullChat);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);

        chatRecyclerView.setAdapter(new ChatRecyclerAdapter(view.getContext(), chatList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets the position of the item that's clicked
                int position = chatRecyclerView.getChildAdapterPosition(view);

                // Gets the chat based on which item got clicked
                Chat clickedChat = chatList.get(position);

                Intent fullChat = new Intent(getContext(), ChatActivity.class);
                fullChat.putExtra("CHAT", clickedChat);
                startActivity(fullChat);
            }
        }));


        chatRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
    }

    private void setChatList(ArrayList<Chat> chatList) {
        this.chatList = chatList;
    }
}
