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
    private String mshortDescription;
    private String mlongDescription;
    private String mPath;


    public static final String TITLE = "title", SHORT_DESC = "price"
            , LONG_DESC = "longDesc", IMAGE_PATH = "imagePath";

    public ItemContent(String id, String sd, String ld, String path) {
        mlongDescription = ld;
        mshortDescription = sd;
        mcourseId = id;
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
                    Log.d("WOW", obj.getString(ItemContent.TITLE));
                    ItemContent course = new ItemContent(obj.getString(ItemContent.TITLE), obj.getString(ItemContent.SHORT_DESC)
                            , obj.getString(ItemContent.LONG_DESC), obj.getString(ItemContent.IMAGE_PATH));
                    courseList.add(course);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }


    public String getCourseId() {
        return mcourseId;
    }

    public String getShortDescription() {
        return mshortDescription;
    }

    public String getId() {
        return mcourseId;
    }

    public String getShortDesc() {
        return mshortDescription;
    }

    public String getLongDesc() {
        return mlongDescription;
    }

    public String getPath() {
        return mPath;
    }

}
