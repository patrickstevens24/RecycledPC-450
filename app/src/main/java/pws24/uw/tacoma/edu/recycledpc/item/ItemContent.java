package pws24.uw.tacoma.edu.recycledpc.item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by patrickstevens on 2/12/17.
 */

public class ItemContent implements Serializable {

    private String mcourseId;
    private String mshortDescription;
    private String mlongDescription;
    private String mprereqs;

    public static final String TITLE = "title", SHORT_DESC = "shortDesc"
            , LONG_DESC = "longDesc", PRE_REQS = "prereqs";

    public ItemContent(String id, String sd, String ld, String pr) {
        mprereqs = pr;
        mlongDescription = ld;
        mshortDescription = sd;
        mcourseId = id;
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
                            , obj.getString(ItemContent.LONG_DESC), obj.getString(ItemContent.PRE_REQS));
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

    public String getPrereqs() {
        return mprereqs;
    }
}
