package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // UI references
    private Button registerButton;
    private Button loginButton;
    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = findViewById(R.id.buttonRegister);
        loginButton = findViewById(R.id.buttonLogin);
        usernameField = findViewById(R.id.inputLoginUsername);
        passwordField = findViewById(R.id.inputLoginPassword);

        // When 'Login' button is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                String forToast = "Username: " + username + "\nPassword: " + password;

                Toast.makeText(getBaseContext(), forToast, Toast.LENGTH_LONG).show();

                finish();
            }
        });


        // When 'Register' button is clicked
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
