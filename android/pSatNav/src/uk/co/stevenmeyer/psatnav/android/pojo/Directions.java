/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.stevenmeyer.psatnav.android.pojo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Steven Meyer <steven@stevenmeyer.co.uk>
 */
public class Directions {
    
    protected static final String API_KEY = "36eb7b2a0e2d426cb9777395aea287ef";
    public static final String LANG_EN = "en";
    public static final String UNIT_MILES = "miles";
    
    public static String getDirections(double source_latitude, double source_longitude, double destination_latitude, double destination_longitude, String lang, String unit) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(Directions.getURL(source_latitude, source_longitude, destination_latitude, destination_longitude, lang, unit)));
        StatusLine statusLine = response.getStatusLine();
        if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            out.close();
            String responseString = out.toString();
            return responseString;
        } else {
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }
    }
    
    protected static String getURL(double source_latitude, double source_longitude, double destination_latitude, double destination_longitude, String lang, String unit) {
        StringBuilder urlString = new StringBuilder();
 
        urlString.append("http://routes.cloudmade.com/");
        urlString.append(Directions.API_KEY);
        urlString.append("/api/0.3/");
        urlString.append(Double.toString(source_latitude));
        urlString.append(",");
        urlString.append(Double.toString(source_longitude));
        urlString.append(",");
        urlString.append(Double.toString(destination_latitude));
        urlString.append(",");
        urlString.append(Double.toString(destination_longitude));
        urlString.append("/car/fastest.js?lang=");
        urlString.append(lang);
        urlString.append("&units=");
        urlString.append(unit);
        
        return urlString.toString();
    }
    
}
