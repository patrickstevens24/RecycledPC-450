/*
 * Recycled PC
 * Team 10
 * TCSS 405b- Spring 2017
 */

package pws24.uw.tacoma.edu.recycledpc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import pws24.uw.tacoma.edu.recycledpc.item.ItemContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link .} interface
 * to handle interaction events.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 *
 * @author Patrick Stevens
 * @author Arthur Panlilio
 */
public class ItemDetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ItemContent pls;

    private TextView mCourseIdTextView;
    private TextView mCourseShortDescTextView;
    private TextView mCourseLongDescTextView;

    private ImageView mItemImageView;


    private OnListFragmentInteractionListener mListener;
    public final static String COURSE_ITEM_SELECTED = "course_selected";

    /**
     * empty constructor
     */
    public ItemDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_item_detail, container, false);
        mCourseIdTextView = (TextView) view.findViewById(R.id.course_item_id);
        mCourseShortDescTextView = (TextView) view.findViewById(R.id.course_short_desc);
        mCourseLongDescTextView = (TextView) view.findViewById(R.id.course_long_desc);
        mItemImageView = (ImageView) view.findViewById(R.id.imageView3);



        return view;
    }

    /**
     * Gets the data online to update the information.
     *
     * @param item is the item to get information from.
     */
    public void updateView(ItemContent item) {
        if (item != null) {
            mCourseIdTextView.setText(item.getId());
            mCourseShortDescTextView.setText(item.getShortDesc());
            mCourseLongDescTextView.setText(item.getLongDesc() + item.getSellerID());
            String imagePath = item.getPath();
            new DownloadImageTask(mItemImageView).execute(imagePath);
        }
    }



    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((ItemContent) args.getSerializable(COURSE_ITEM_SELECTED));

        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ItemContent item);
    }

    /**
     * This task connects online so the item detail can download the picture of that item online.
     *
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
