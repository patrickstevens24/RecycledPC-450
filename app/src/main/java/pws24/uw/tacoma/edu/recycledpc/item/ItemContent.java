/*
 * Recycled PC
 * Team 10
 * TCSS 405b- Spring 2017
 */

package pws24.uw.tacoma.edu.recycledpc.item;

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
 * @author Patrick Stevens
 */

public class ItemContent implements Serializable {

    private String mcourseId;
    private String mPrice;
    private String mlongDescription;



    private int msellerID;
    private String mPath;


    public static final String TITLE = "title", SHORT_DESC = "price"
            , LONG_DESC = "longDesc", IMAGE_PATH = "imagePath", SELLER_ID = "sellerID";

    public ItemContent(String id, String price, String ld, int sellerID, String path) {
        if (ld.length() > 5) {
            mlongDescription = ld;
        } else {
            throw new IllegalArgumentException("Description too short");
        }
        if (checkPrice(price)) {
            mPrice = price;
        } else {
            throw new IllegalArgumentException("Price must contain a number");
        }

        if (checkName(id)) {
            mcourseId = id;
        } else {
            throw new IllegalArgumentException("name");
        }
        msellerID = sellerID;
        mPath = path;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.+
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<ItemContent> courseList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    ItemContent course = new ItemContent(obj.getString(ItemContent.TITLE), obj.getString(ItemContent.SHORT_DESC)
                            , obj.getString(ItemContent.LONG_DESC), obj.getInt(ItemContent.SELLER_ID), obj.getString(ItemContent.IMAGE_PATH));
                    courseList.add(course);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    private boolean checkPrice(String s) {
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    private boolean checkName(String s) {
        if (s.length() > 2) {
            return true;
        }
        return false;
    }



    public String getCourseId() {
        return mcourseId;
    }

    public String getShortDescription() {
        return mPrice;
    }

    public String getId() {
        return mcourseId;
    }

    public String getShortDesc() {
        return mPrice;
    }

    public String getLongDesc() {
        return mlongDescription;
    }

    public int getSellerID() {
        return msellerID;
    }

    public void setSellerID(int msellerID) {
        this.msellerID = msellerID;
    }

    public String getPath() {
        return mPath;
    }

}
