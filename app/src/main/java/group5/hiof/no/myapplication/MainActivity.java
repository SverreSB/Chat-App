package group5.hiof.no.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static group5.hiof.no.myapplication.R.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        //Will change this logic later.
        //Should check if user is logged in.
        boolean loggedIn = false;

        if(!loggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        // Get a reference to the navigation controller
        NavController navController = Navigation.findNavController(findViewById(id.navHostFragment));

        // Pass navigation controller to a function to set up bottom navigation
        setupBottomNavigation(navController);
    }

    // Set up the bottom navigation
    private void setupBottomNavigation(NavController navController) {

        BottomNavigationView bottomNav = findViewById(id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNav, navController);

    }

}
