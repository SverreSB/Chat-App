package group5.hiof.no.myapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import group5.hiof.no.myapplication.model.Chat;
import group5.hiof.no.myapplication.model.User;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap = null;

    private LatLng hiof = new LatLng(59.12797849, 11.35272861);
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
        gMap.addMarker(new MarkerOptions().position(hiof).title("Marker on HIOF"));

        // Add markers when the map is ready
        getActiveChats();
    }


    private void getActiveChats() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String currentUserUid = mAuth.getUid();

        db.collection("users")
                .document(currentUserUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            user = task.getResult().toObject(User.class);
                            getChatPartners();
                        }
                    }
                });
    }

    private void getChatPartners() {
        Toast.makeText(getContext(), String.valueOf(user.getActiveChats().size()), Toast.LENGTH_LONG).show();
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

                            markers.put(marker, 1);
                            gMap.addMarker(new MarkerOptions().position(marker));
                        }
                    });
        }
    }

}
