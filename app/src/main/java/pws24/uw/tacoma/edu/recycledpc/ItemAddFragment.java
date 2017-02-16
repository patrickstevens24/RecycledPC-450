package pws24.uw.tacoma.edu.recycledpc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URLEncoder;


import static android.app.Activity.RESULT_OK;
import static pws24.uw.tacoma.edu.recycledpc.R.id.imageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@l factory method to
 * create an instance of this fragment.
 */
public class ItemAddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText mCourseIdEditText;
    private EditText mCourseShortDescEditText;
    private EditText mCourseLongDescEditText;
    private EditText mCoursePrereqsEditText;

    private Bitmap bitmap;

    private ImageView imageView;


    private final static String COURSE_ADD_URL
            = "http://cssgate.insttech.washington.edu/~_450bteam10/addItem2.php?";

    // TODO: Rename and change types of parameters


    public ItemAddFragment() {
        // Required empty public constructor
    }
    private ItemAddListener mListener;

    public interface ItemAddListener {
        public void addCourse(String url, Bitmap bitmap);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemAddFragment newInstance(String param1, String param2) {
        ItemAddFragment fragment = new ItemAddFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(COURSE_ADD_URL);

        try {

            String courseId = mCourseIdEditText.getText().toString();
            sb.append("title=");
            sb.append(courseId);


            String courseShortDesc = mCourseShortDescEditText.getText().toString();
            sb.append("&price=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));


            String courseLongDesc = mCourseLongDescEditText.getText().toString();
            sb.append("&longDesc=");
            sb.append(URLEncoder.encode(courseLongDesc, "UTF-8"));


        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_add, container, false);

        mCourseIdEditText = (EditText) v.findViewById(R.id.add_course_id);
        mCourseShortDescEditText = (EditText) v.findViewById(R.id.add_course_short_desc);
        mCourseLongDescEditText = (EditText) v.findViewById(R.id.add_course_long_desc);
        imageView = (ImageView)v.findViewById(R.id.upload_preview);

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        Button addCourseButton = (Button) v.findViewById(R.id.add_course_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addCourse(url, bitmap);
            }
        });

        Button uploadButton = (Button) v.findViewById(R.id.upload_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        return v;
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Context applicationContext = HomeActivity.getContextOfApplication();
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemAddListener) {
            mListener = (ItemAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CourseAddListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
