package group5.hiof.no.myapplication.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class LocationWorker {

    private Context context;

    public LocationWorker(Context context) {
        this.context = context;
    }


    @SuppressLint("MissingPermission")
    public Location getLocation() {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return lastLocation;
    }


    public String reverseGeocode(Location location) {
        String addressDescription = null;

        try  {

            Geocoder geocoder = new Geocoder(context);
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);

            if(!addressList.isEmpty()) {
                Address firstAddress = addressList.get(0);
                addressDescription = firstAddress.getCountryName();
            }
            else {
                return "NOT FOUND";
            }
        }
        catch (IOException ex) {
            Log.e("Worker.reverseGeocode", "IOException Error");
        }
        catch (Exception ex) {
            Log.e("Worker.reverseGeocode", "Error");
        }

        return addressDescription;
    }

}
