/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.stevenmeyer.psatnav.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.stevenmeyer.psatnav.android.pojo.Directions;

/**
 *
 * @author Steven Meyer <steven@stevenmeyer.co.uk>
 */
public class DirectionsActivity extends Activity {
    
    /*
     * The destination GeoPoint
     */
    public static final String DESTINATION_LATITUDE = "destinationLatitute";
    public static final String DESTINATION_LONGITUDE = "destinationLongitude";
    public static final String SOURCE_LATITUDE = "sourceLatitute";
    public static final String SOURCE_LONGITUDE = "sourceLongitude";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.directions);
        Bundle extras = getIntent().getExtras();
        /*int destLatitude = (int)(extras.getDouble(DESTINATION_LATITUDE) * 1E6d);
        int destLongitude = (int)(extras.getDouble(DESTINATION_LONGITUDE) * 1E6d);
        int sourceLatitude = (int)(extras.getDouble(SOURCE_LATITUDE) * 1E6d);
        int sourceLongitude = (int)(extras.getDouble(SOURCE_LONGITUDE) * 1E6d);
        
        GeoPoint source = new GeoPoint(sourceLatitude, sourceLongitude);
        GeoPoint destination = new GeoPoint(destLatitude, destLongitude);*/
        
        String directions = null;
        try {
            directions = Directions.getDirections(extras.getDouble(SOURCE_LATITUDE), extras.getDouble(SOURCE_LONGITUDE), extras.getDouble(DESTINATION_LATITUDE), extras.getDouble(DESTINATION_LONGITUDE), Directions.LANG_EN, Directions.UNIT_MILES);
        } catch (IOException ex) {
            Log.e("psatnav", "Error fetching directions" + ex.getMessage(), ex);
        }
        if (null == directions) {
            return;
        }
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(directions);
        /*Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(directions), Toast.LENGTH_SHORT);
        toast.show();*/
        direct(directions);
    }
    
    public void direct(String jsonString) {
        JSONObject json;
        try {
            json = new JSONObject(jsonString);
            if (0 == json.get("status")) {
                JSONArray instructions = json.getJSONArray("route_instructions");
                JSONArray first = instructions.getJSONArray(0);
                JSONArray second = instructions.getJSONArray(1);
                String[] firstSplit = first.toString().split(",");
                String metresToNode = firstSplit[1];
                String unitsToNode = firstSplit[4];
                String[] secondSplit = second.toString().split(",");
                String nextRoad = secondSplit[0];
                String turnType = secondSplit[7];
                
                
            } else {
                Log.e("psatnav", "status is not OK");
            }
        } catch (JSONException ex) {
            Log.e("psatnav", "Error parsing JSON response", ex);
        }
    }
    
}
