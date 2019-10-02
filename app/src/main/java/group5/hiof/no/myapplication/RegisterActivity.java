package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing buttons and text fields for inputs
        Button goBackButton = findViewById(R.id.buttonGoBack);
        Button registerUserButton = findViewById(R.id.buttonRegisterUser);

        final EditText usernameField = findViewById(R.id.inputRegisterUsername);
        final EditText passwordField = findViewById(R.id.inputRegisterPassword);
        final EditText repeatPasswordField = findViewById(R.id.inputRegisterRepeatPassword);


        // Getting font from assets to use for go back to login screen
        Typeface font = Typeface.createFromAsset( getAssets(), "fonts/fontawesome-webfont.ttf" );

        goBackButton.setTypeface(font);

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
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                String repeatedPassword = repeatPasswordField.getText().toString();
                String forToast = "Username: " + username + "\nPassword: " + password + "\nRepeated Password: " + repeatedPassword;

                // Add for saving fields to Database


                Toast.makeText(getBaseContext(), forToast, Toast.LENGTH_LONG).show();

                finish();
            }
        });


    }
}
