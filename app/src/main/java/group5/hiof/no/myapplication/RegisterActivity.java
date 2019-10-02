package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button goBackButton = findViewById(R.id.buttonGoBack);
        Button registerUserButton = findViewById(R.id.buttonRegisterUser);

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
                /*
                    Logic for saving fields to Database
                 */
                finish();
            }
        });


    }
}
