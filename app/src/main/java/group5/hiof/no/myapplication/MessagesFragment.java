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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.model.DocumentCollections;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private final String TAG = "MESSAGEFRAGMENT";
    private ArrayList<Chat> chatList;
    private ArrayList<String> chatIdList;
    private RecyclerView chatRecyclerView;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private User user;

    private FloatingActionButton newMessage;


    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        chatList = new ArrayList<>();
        chatIdList = new ArrayList<>();
        user = new User();
        mAuth = FirebaseAuth.getInstance();
        getCurrentUser();

    }

    private void getCurrentUser() {
        db = FirebaseFirestore.getInstance();

        //Getting chats from current users list of chats and finds the chats by id
        db.collection("users")
                .document(mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            user = task.getResult().toObject(User.class);
                            createFireStoreReadListener();
                        }
                    }
                });
    }

    private void createFireStoreReadListener() {
        ArrayList<String> activeChatsID = user.getActiveChats();
        if(activeChatsID != null) {
            for (final String chatID : activeChatsID) {
                db = FirebaseFirestore.getInstance();
                DocumentReference currentChatDocumentReference = db.collection("chats").document(chatID);
                currentChatDocumentReference
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Chat chat = task.getResult().toObject(Chat.class);
                                    chat.setUid(chatID);
                                    if(chatIdList.indexOf(chat.getUid()) == -1) {
                                        chatIdList.add(chat.getUid());
                                        chatList.add(chat);
                                    }
                                }
                                chatRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentUser();
        createFireStoreReadListener();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        //chatList = Chat.getChats();

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

        chatRecyclerAdapter = new ChatRecyclerAdapter(view.getContext(), chatList, new View.OnClickListener() {
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
        });

        chatRecyclerView.setAdapter(chatRecyclerAdapter);
        chatRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
    }

}
