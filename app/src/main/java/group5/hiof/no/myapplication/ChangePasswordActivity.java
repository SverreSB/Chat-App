package group5.hiof.no.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPasswordField;
    EditText newPasswordField;
    EditText repeatPasswordField;
    Button changePasswordButton;
    Button goBackToProfileFragmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordField = findViewById(R.id.inputOldPassword);
        newPasswordField = findViewById(R.id.inputNewPassword);
        repeatPasswordField = findViewById(R.id.inputRepeatPassword);
        changePasswordButton = findViewById(R.id.buttonChangePassword);
        goBackToProfileFragmentButton = findViewById(R.id.buttonGoBack);

        goBackToProfileFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting text from input fields
                String oldPassword = oldPasswordField.getText().toString();
                String newPassword = newPasswordField.getText().toString();
                String repeatPassword = repeatPasswordField.getText().toString();

                if (oldPassword.equals(newPassword)) {
                    Toast.makeText(getBaseContext(), "Can't change to same password", Toast.LENGTH_LONG).show();
                }
                else if (newPassword.equals(repeatPassword)) {
                    reauthenticateAndUpdatePassword(oldPassword, newPassword, repeatPassword);
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Passwords didn't match", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void reauthenticateAndUpdatePassword(String oldPassword, final String newPassword, String repeatPassword) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()) {
                   updatePassword(user, newPassword);
               }
               else {
                   Toast.makeText(getBaseContext(), "Invalid old password", Toast.LENGTH_LONG).show();
               }
           }
        });
    }

    private void updatePassword(FirebaseUser user, String password) {
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Password is updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "Couldn't update password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
