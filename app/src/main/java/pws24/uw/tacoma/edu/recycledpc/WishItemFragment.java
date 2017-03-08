/*
 * Recycled PC
 * Team 10
 * TCSS 405b- Spring 2017
 */

package pws24.uw.tacoma.edu.recycledpc;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import pws24.uw.tacoma.edu.recycledpc.data.NameDB;
import pws24.uw.tacoma.edu.recycledpc.item.ItemContent;
import pws24.uw.tacoma.edu.recycledpc.wishItem.WishItemContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 *
 * @author Patrick Stevens
 */
public class WishItemFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String WISH_URL = "http://cssgate.insttech.washington.edu/~_450bteam10/getWishList.php?";
    private static final String ADD_WISH_URL = "http://cssgate.insttech.washington.edu/~_450bteam10/addWishItem.php?";

    private int mColumnCount = 2;

    private NameDB mNameDB;
    private TextView mWishView;
    private EditText mWishAdd;
    private WishItemFragment.WishItemListener mListener;
    private RecyclerView mRecyclerView;

    public WishItemFragment() {

    }




    public interface WishItemListener {
        public void updateWishList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }



    /**
     * Downloads the item information from the database so that it can be displayed in a list.
     *
     */
    private class DownloadCoursesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), "Error with server", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            List<WishItemContent> wishItemList = new ArrayList<WishItemContent>();
            result = WishItemContent.parseCourseJSON(result, wishItemList);
            if (result == null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < wishItemList.size(); i++ ) {
                    sb.append(wishItemList.get(i).getmName() + "\n\n");
                }
                mWishView.setText(sb.toString());
                return;
            }


        }
    }








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wish_item, container, false);
        mWishView = (TextView)v.findViewById(R.id.wishItem);
        mWishAdd = (EditText)v.findViewById(R.id.addWishItem);
        mNameDB = new NameDB(getActivity());
        Button button = (Button)v.findViewById(R.id.add_wish_item_button);
        DownloadCoursesTask task = new DownloadCoursesTask();
        StringBuilder sb = new StringBuilder(WISH_URL);
        sb.append("userID=");
        String userID = mNameDB.getID()+"";
        try {
            sb.append(URLEncoder.encode(userID, "UTF-8"));
        } catch (Exception e){
            Log.e("Error", e.toString());
        }
        String url = sb.toString();
        task.execute(new String[]{url});
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWishItem();
            }
        });
        return v;
    }


    private void addWishItem(){
        UploadWishItemTask task = new UploadWishItemTask();
        StringBuilder sb = new StringBuilder(ADD_WISH_URL);
        try {
            sb.append("userID=");
            String userID = mNameDB.getID()+"";
            sb.append(URLEncoder.encode(userID, "UTF-8"));
            sb.append("&name=");
            String name = mWishAdd.getText().toString();
            sb.append(URLEncoder.encode(name, "UTF-8"));
        } catch (Exception e){
            Log.e("Error", e.toString());
        }
        String wishUrl = sb.toString();
        task.execute(new String[] {wishUrl});
    }

    private class UploadWishItemTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getActivity(), "Item added to wishlist"
                            , Toast.LENGTH_LONG)
                            .show();
                            updateWishList();

                } else {
                    Toast.makeText(getActivity(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updateWishList(){
        getFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }





    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
