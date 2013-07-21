/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.stevenmeyer.psatnav.android.pojo;

import android.content.Context;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import java.util.UUID;

/**
 *
 * @author Steven
 */
public class Pebble {
    
    protected static final byte[] UUID_BYTES = { (byte)114, (byte)187, (byte)142, (byte)132, (byte)134, (byte)97, (byte)76, (byte)229, (byte)187, (byte)198, (byte)114, (byte)144, (byte)230, (byte)81, (byte)239, (byte)00 };
    protected static final UUID APP_UUID = UUID.nameUUIDFromBytes(UUID_BYTES);
    protected static final int ROAD_NAME = 0;
    protected static final int TURN_TYPE = 1;
    protected static final int METRES_TO_TURN = 2;
    protected static final int UNITS_TO_TURN = 3;
    
    public static void pushInstruction(String nextRoad, String turnType, String metresToTurn, String unitsToTurn, Context context) {
        PebbleDictionary data = new PebbleDictionary();
        data.addString(Pebble.ROAD_NAME, nextRoad);
        data.addString(Pebble.TURN_TYPE, turnType);
        data.addString(Pebble.METRES_TO_TURN, metresToTurn);
        data.addString(Pebble.UNITS_TO_TURN, unitsToTurn);
        PebbleKit.sendDataToPebble(context, APP_UUID, data);
    }
}
