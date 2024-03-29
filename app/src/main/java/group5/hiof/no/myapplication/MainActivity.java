package group5.hiof.no.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import group5.hiof.no.myapplication.utility.LocationWorker;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.AsyncTask;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;

import static group5.hiof.no.myapplication.R.*;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int LOCATION_PERMISSION_ID = 1;
    private AsyncTaskWorker asyncTaskWorker;

    public static double LATITUDE;
    public static double LONGITUDE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

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


    @AfterPermissionGranted(LOCATION_PERMISSION_ID)
    private void askForLocationPermission() {

        asyncTaskWorker = new AsyncTaskWorker();

        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Create an intent to send the location information through activities
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
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
        asyncTaskWorker.execute();
    }


    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }




    public class AsyncTaskWorker extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            LocationWorker worker = new LocationWorker(MainActivity.this);

            Location foundLocation = worker.getLocation();
            LATITUDE = foundLocation.getLatitude();
            LONGITUDE = foundLocation.getLongitude();

            String address = worker.reverseGeocode(foundLocation);

            return true;
        }
    }

}
