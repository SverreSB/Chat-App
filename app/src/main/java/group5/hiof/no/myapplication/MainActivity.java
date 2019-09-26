package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Will change this logic later.
        //Should check if user is logged in.
        boolean loggedIn = false;
        if(!loggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
