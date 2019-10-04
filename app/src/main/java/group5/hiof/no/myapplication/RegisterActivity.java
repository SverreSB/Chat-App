package group5.hiof.no.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // UI references
    private Button goBackButton;
    private Button registerUserButton;
    private EditText usernameField;
    private EditText passwordField;
    private EditText repeatPasswordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        goBackButton = findViewById(R.id.buttonGoBack);
        registerUserButton = findViewById(R.id.buttonRegisterUser);

        usernameField = findViewById(R.id.inputRegisterUsername);
        passwordField = findViewById(R.id.inputRegisterPassword);
        repeatPasswordField = findViewById(R.id.inputRegisterRepeatPassword);

        mAuth = FirebaseAuth.getInstance();


        // Button for going back to previous activity. This should be returning to login screen
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
            Button for registering user. Saves data given by user to database and
            returns to previous activity(login screen)

         */
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                createUser(email, password);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        isLoggedIn(currentUser);
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Not working", Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                }
            }
        });
    }

    private void isLoggedIn(FirebaseUser user) {
        if (user != null) {
            finish();
        }
    }
}
