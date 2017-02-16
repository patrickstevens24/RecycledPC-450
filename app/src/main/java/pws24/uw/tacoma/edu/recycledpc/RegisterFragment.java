package pws24.uw.tacoma.edu.recycledpc;

import android.content.Context;
import android.content.Intent;
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
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static final String REGISTER_URL = "http://cssgate.insttech.washington.edu/~_450bteam10/register.php?";
    private EditText etEmail;
    private EditText etEmailConfirm;
    private EditText etPassword;
    private EditText etPasswordConfirm;



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
    // TODO: Rename and change types and number of parameters
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
        etEmailConfirm = (EditText)v.findViewById(R.id.conEmail);
        etPassword = (EditText)v.findViewById(R.id.regPassword);
        etPasswordConfirm = (EditText)v.findViewById(R.id.conPassword);
        Button b = (Button) v.findViewById(R.id.register_account);
        b.setOnClickListener(this);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void onClick(View arg0) {

        if (!(etEmail.getText().toString().equals(etEmailConfirm.getText().toString()))) {
            Toast.makeText(getActivity(),"Emails do not match",Toast.LENGTH_SHORT).show();
        }
        else if(!(etPassword.getText().toString().equals(etPasswordConfirm.getText().toString()))) {
            Toast.makeText(getActivity(),"Passwords do not match",Toast.LENGTH_SHORT).show();
        } else {
            Log.d("WOW", "WHOHOAHOHAO");
            String url = buildRegisterURL();
            new RegisterFragment.AsyncRegister().execute(url);
        }
    }

    private String buildRegisterURL(){
        StringBuilder sb = new StringBuilder(REGISTER_URL);
        try{
            String email = etEmail.getText().toString();
            sb.append("email=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            String password = etPassword.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));
        } catch(Exception e) {

        }

        return sb.toString();
    }

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
                JSONObject jsonObject = new JSONObject(result);//Why isnt this string converting into json object?
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
