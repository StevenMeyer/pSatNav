package uk.co.stevenmeyer.psatnav.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity
{
    private double myLocationLat = 0d;
    private double myLocationLon = 0d;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        Location lastKnownLocation =
        locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        myLocationLat = lastKnownLocation.getLatitude();
        myLocationLon = lastKnownLocation.getLongitude();
    }
    
    public void startRoute(View view) {
        EditText destination = (EditText) findViewById(R.id.edittext_destination);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(destination.getText().toString(), 1);
        } catch (IOException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (null == addresses) {
            return;
        }
        Address address = addresses.get(0);
        
        Intent next = new Intent(this, DirectionsActivity.class);
        next.putExtra(DirectionsActivity.DESTINATION_LATITUDE, address.getLatitude());
        next.putExtra(DirectionsActivity.DESTINATION_LONGITUDE, address.getLongitude());
        next.putExtra(DirectionsActivity.SOURCE_LATITUDE, myLocationLat);
        next.putExtra(DirectionsActivity.SOURCE_LONGITUDE, myLocationLon);
        startActivity(next);
    }
}
