/*
 * Recycled PC
 * Team 10
 * TCSS 405b- Spring 2017
 */

package pws24.uw.tacoma.edu.recycledpc;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 * This fragment enables the user to register an account
 *
 * @author Arthur Panlilio
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    //URL that leads to the register php file.
    public static final String REGISTER_URL = "http://cssgate.insttech.washington.edu/~_450bteam10/register.php?";
    //These variables are to be compared to see if the information matches
    private EditText etEmail;
    private EditText etPassword;
    private EditText etFirstName;
    private EditText etLastName;



    private RegisterListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface RegisterListener{
        public void insertEmail(String email);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        etEmail = (EditText)v.findViewById(R.id.regEmail);
        etPassword = (EditText)v.findViewById(R.id.regPassword);
        etFirstName = (EditText)v.findViewById(R.id.firstName);
        etLastName = (EditText)v.findViewById(R.id.lastName);
        Button b = (Button) v.findViewById(R.id.register_account);
        b.setOnClickListener(this);
        return v;
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof RegisterListener) {
            mListener = (RegisterListener)context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement CourseAddListener");
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
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Checks if the username and passwords match the confirmation information.
     *
     * @param arg0
     */
    public void onClick(View arg0) {
        if (etPassword.getText().toString().trim().length() <= 0) {
            Toast.makeText(getContext(), "You did not enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etFirstName.getText().toString().trim().length() <= 0) {
            Toast.makeText(getContext(), "You did not enter a first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etLastName.getText().toString().trim().length() <= 0) {
            Toast.makeText(getContext(), "You did not enter a last name", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = buildRegisterURL(arg0);

        new RegisterFragment.AsyncRegister().execute(url);

    }

    /**
     * Creates a url command for the php to register
     *
     * @param v is the view
     * @return the url for the command.
     */
    private String buildRegisterURL(View v){
        StringBuilder sb = new StringBuilder(REGISTER_URL);
        try{
            String email = etEmail.getText().toString();
            sb.append("email=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            String password = etPassword.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

            String firstName = etFirstName.getText().toString();
            sb.append("&firstName=");
            sb.append(URLEncoder.encode(firstName, "UTF-8"));

            String lastName = etLastName.getText().toString();
            sb.append("&lastName=");
            sb.append(URLEncoder.encode(lastName, "UTF-8"));

        } catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        Log.d("OOO", sb.toString());
        return sb.toString();
    }

    /**
     * Gets the url string and executes the php files
     * to upload the register information to the database.
     *
     */
    private class AsyncRegister extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url:urls){
                try{
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream conent = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(conent));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to add course, Reason: "
                            + e.getMessage();
                }finally {
                    if(urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getContext(), "Account Registration Successful"
                            , Toast.LENGTH_LONG)
                            .show();
                    mListener.insertEmail(etEmail.getText().toString());
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), ""
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
