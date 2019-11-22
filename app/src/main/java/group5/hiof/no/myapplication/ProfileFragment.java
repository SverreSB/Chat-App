package group5.hiof.no.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.model.Message;
import group5.hiof.no.myapplication.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    // UI references
    Button changePasswordButton;
    Button signOutButton;
    Button deleteAccountButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Assigning values to Firebase variables
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Assigning values to UI references
        changePasswordButton = view.findViewById(R.id.buttonOpenChangePassword);
        signOutButton = view.findViewById(R.id.buttonSignOut);
        deleteAccountButton = view.findViewById(R.id.buttonDeleteAccount);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        // When pressing 'Sign Out' button
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Testing delete account", Toast.LENGTH_LONG).show();
                final CollectionReference userReference = db.collection("users");
                final CollectionReference chatReference = db.collection("chats");
                final String userID = mAuth.getUid();

                // Validate password

                // Look through friends in both sender and receiver to remove from array
                userReference.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            for (final String chatID : user.getActiveChats()) {
                                chatReference.document(chatID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            Chat currentChat = task.getResult().toObject(Chat.class);

                                            int indexOfCurrentUser = currentChat.getParticipants().indexOf(userID);
                                            int indexOfFriend;

                                            if (indexOfCurrentUser == 1) {
                                                indexOfFriend = 0;
                                            }
                                            else {
                                                indexOfFriend = 1;
                                            }

                                            String friendID = currentChat.getParticipants().get(indexOfFriend);
                                            deleteFriendFromList(userID, friendID);
                                            deleteChatFromList(chatID, friendID);
                                            deleteMessages(chatID);
                                            deleteChat(chatID);
                                        }
                                    }
                                });
                            }
                            deleteUser(userID);
                        }

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void deleteFriendFromList(String currentUserID, String friendID) {
        db.collection("users").document(friendID).update("friends", FieldValue.arrayRemove(currentUserID));
    }

    private void deleteChatFromList(String chatID, String friendID) {
        db.collection("users").document(friendID).update("activeChats", FieldValue.arrayRemove(chatID));
    }

    private void deleteMessages(final String chatID) {
        db.collection("chats").document(chatID).collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<DocumentSnapshot> documentSnapshot = task.getResult().getDocuments();
                    for(DocumentSnapshot message : documentSnapshot) {
                        db.collection("chats").document(chatID).collection("messages").document(message.getId()).delete();
                    }

                }
            }
        });
    }

    private void deleteChat(String chatID) {
        db.collection("chats").document(chatID).delete();
    }

    private void deleteUser(String userID) {
        db.collection("users").document(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
