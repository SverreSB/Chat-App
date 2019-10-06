package group5.hiof.no.myapplication.database;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDB {

    public UserDB() {
        // Empty constructor
    }

    // Creates a document in the 'users' collection in Firestore
    public void writeUserToDB(String username, String email, double lat, double lng, String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("latitude", lat);
        user.put("longitude", lng);

        db.collection("users").document(id).set(user);
    }

}
