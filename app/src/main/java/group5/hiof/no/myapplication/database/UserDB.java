package group5.hiof.no.myapplication.database;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import group5.hiof.no.myapplication.model.User;

public class UserDB {

    public UserDB() {
        // Empty constructor
    }

    // Creates a document in the 'users' collection in Firestore
    public void writeUserToDB(User user, String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(id).set(user);
    }

}
