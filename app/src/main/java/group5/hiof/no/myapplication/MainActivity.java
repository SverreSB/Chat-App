package group5.hiof.no.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import static group5.hiof.no.myapplication.R.*;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int LOCATION_PERMISSION_ID = 2;

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        //Will change this logic later.
        //Should check if user is logged in.
        /*
        if(!loggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        */

        askForLocationPermission();

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


    //@AfterPermissionGranted(LOCATION_PERMISSION_ID)
    private void askForLocationPermission() {

        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                    this,
                    "We need this permission for you to continue.",
                    LOCATION_PERMISSION_ID,
                    Manifest.permission.ACCESS_FINE_LOCATION
            );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // Handle granted permission
    }


    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
