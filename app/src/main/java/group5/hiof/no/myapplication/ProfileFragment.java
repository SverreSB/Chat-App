package group5.hiof.no.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    // UI references
    /*EditText oldPasswordField;
    EditText newPasswordField;
    EditText repeatPasswordField;
    Button changePasswordButton;*/
    Button signOutButton;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Assigning values to UI references

        signOutButton = view.findViewById(R.id.buttonSignOut);


        // When pressing 'Change Password' button
        /*
        changePasswordButton = view.findViewById(R.id.buttonChangePassword);
        oldPasswordField = view.findViewById(R.id.profileOldPassword);
        newPasswordField = view.findViewById(R.id.profileNewPassword);
        repeatPasswordField = view.findViewById(R.id.profileRepeatPassword);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Getting text from input fields
               String oldPassword = oldPasswordField.getText().toString();
               String newPassword = newPasswordField.getText().toString();
               String repeatPassword = repeatPasswordField.getText().toString();

               if (oldPassword.equals(newPassword)) {
                   Toast.makeText(getContext(), "Can't change to same password", Toast.LENGTH_LONG).show();
               }
               else if (newPassword.equals(repeatPassword)) {
                   reauthenticateAndUpdatePassword(oldPassword, newPassword, repeatPassword);
               }
               else {
                   Toast.makeText(getContext(), "Passwords didn't match", Toast.LENGTH_LONG).show();
               }
           }
        });*/

        // When pressing 'Sign Out' button
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return view;
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
                   Toast.makeText(getContext(), "Invalid old password", Toast.LENGTH_LONG).show();
               }
           }
        });
    }

    private void updatePassword(FirebaseUser user, String password) {
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Password is updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Couldn't update password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
