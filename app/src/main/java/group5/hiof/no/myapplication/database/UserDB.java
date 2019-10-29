package group5.hiof.no.myapplication.database;

import com.google.firebase.firestore.FirebaseFirestore;

import group5.hiof.no.myapplication.model.User;

public class UserDB {
    FirebaseFirestore db;

    public UserDB() {
        // Empty constructor
    }

    // Creates a document in the 'users' collection in Firestore
    public void writeUserToDB(User user, String id) {

        db = FirebaseFirestore.getInstance();

        db.collection("users").document(id).set(user);
    }
}
