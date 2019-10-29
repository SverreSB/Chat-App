package group5.hiof.no.myapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.model.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap = null;

    private HashMap<LatLng, Integer> markers = new HashMap<>();
    private ArrayList<String> activeChats;
    private ArrayList<String> receivers = new ArrayList<>();
    private User user;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Get SupportMapFragment and get notified when the map is ready
        FragmentManager fragmentManager = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }


    // Overridden method from 'OnMapReadyCallback' interface
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Add markers when the map is ready
        getActiveChats();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActiveChats();
    }

    // Gets all the active chats for the logged in user
    private void getActiveChats() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String currentUserUid = mAuth.getUid();
        if(currentUserUid != null) {
            db.collection("users")
                    .document(currentUserUid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                user = task.getResult().toObject(User.class);
                                getChatPartners();
                            }
                        }
                    });
        }
    }


    // Gets all the current users chatpartners
    private void getChatPartners() {
        activeChats = user.getActiveChats();

        if(activeChats != null) {
            for(String chat : activeChats) {
                db = FirebaseFirestore.getInstance();

                db.collection("chats")
                        .document(chat)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Chat temp = task.getResult().toObject(Chat.class);

                                mAuth = FirebaseAuth.getInstance();
                                String currentUserUid = mAuth.getUid();

                                if(currentUserUid.equals(temp.getParticipants().get(1))) {
                                    receivers.add(temp.getParticipants().get(0));
                                }
                                else {
                                    receivers.add(temp.getParticipants().get(1));
                                }

                                createMarkers();
                            }
                        });

            }
        }
    }


    // Eventually creates markers for all chatpartners
    private void createMarkers() {
        for(String receiver : receivers) {
            db = FirebaseFirestore.getInstance();

            db.collection("users")
                    .document(receiver)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            User temp = task.getResult().toObject(User.class);
                            LatLng marker = new LatLng(temp.getLatitude(), temp.getLongitude());

                            gMap.addMarker(new MarkerOptions().position(marker));
                        }
                    });
        }
    }
}
