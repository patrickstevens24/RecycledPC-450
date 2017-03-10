/*
 * Recycled PC
 * Team 10
 * TCSS 405b- Spring 2017
 */

package pws24.uw.tacoma.edu.recycledpc.wishItem;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * Contains the contents of the item objects.
 *
 * @author Arthur
 */

public class WishItemContent implements Serializable {




    private int mUserID;
    private String mName;

    public static final String NAME = "name" , User_ID = "userID";

    public WishItemContent(String name) {

        mName = name;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.+
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<WishItemContent> WishList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    WishItemContent course = new WishItemContent((obj.getString(WishItemContent.NAME)));
                    WishList.add(course);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public int getMUserID() {
        return mUserID;
    }

    public void setMUserID(int mUserID) {
        this.mUserID = mUserID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }



}
