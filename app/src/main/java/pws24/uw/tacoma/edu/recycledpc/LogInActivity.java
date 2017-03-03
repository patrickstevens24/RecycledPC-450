/*
 * Recycled PC
 * Team 10
 * TCSS 405b- Spring 2017
 */

package pws24.uw.tacoma.edu.recycledpc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

import pws24.uw.tacoma.edu.recycledpc.data.NameDB;
import pws24.uw.tacoma.edu.recycledpc.item.ItemContent;

/**
 *The login activity is the login screen.
 *It has a button that inflates the register fragment.
 *
 * @author Arthur Panlilio
 */
public class LogInActivity extends AppCompatActivity implements RegisterFragment.RegisterListener{

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    // LOGIN_URL connects to the php file.
    public static final String LOGIN_URL = "http://cssgate.insttech.washington.edu/~_450bteam10/login.php?";
    private static final String STORE_URL = "http://cssgate.insttech.washington.edu/~_450bteam10/storeName.php?cmd=names";
    private EditText etEmail;
    private EditText etPassword;
    private NameDB mNameDB;

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mNameDB = new NameDB(this);
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);

    }

    /**
     * Checks the login information
     *
     * @param arg0
     */
    public void checkLogin(View arg0) {

        String url = buildLogInURL();
        new AsyncLogin().execute(url);
    }

    /**
     * inserts the email.
     *
     * @param email
     */
    public void insertEmail(String email){
        EditText myTextBox = (EditText) findViewById(R.id.email);
        myTextBox.setText(email);
    }

    /**
     * Checks if the register button was pressed.
     *
     * @param arg0
     */
    public void checkRegister(View arg0) {
        RegisterFragment registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_log_in, registerFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * builds the url command
     *
     * @return the url command for the php file to login
     */
    private String buildLogInURL(){
        StringBuilder sb = new StringBuilder(LOGIN_URL);
        try{
            String email = etEmail.getText().toString();
            sb.append("email=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            String password = etPassword.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));
        } catch(Exception e) {

        }
        Log.d("OOO", sb.toString());
        return sb.toString();
    }

    /**
     * Connects online to login the user
     *
     */
    private class AsyncLogin extends AsyncTask<String, String, String>{

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
                Log.d("OOOA", status);
                if (status.equals("success")) {

                    Toast.makeText(getApplicationContext(), "Login Successful"
                            , Toast.LENGTH_LONG)
                            .show();
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .apply();
                    StringBuilder sb = new StringBuilder(STORE_URL);
                    sb.append("&email=");
                    String email = etEmail.getText().toString();
                    try {
                        sb.append(URLEncoder.encode(email, "UTF-8"));
                    } catch(Exception e) {

                    }
                    new StoreName().execute(new String[]{sb.toString()});

                } else {
                    Toast.makeText(getApplicationContext(), ""
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class StoreName extends AsyncTask<String, Void, String> {

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
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }



            String fName, lName;
            try {
                JSONObject obj = new JSONObject(result);
                Log.d("NO",obj.getString("firstName"));
                mNameDB.insertName(obj.getString("firstName"), obj.getString("lastName"));
                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intent);
                LogInActivity.this.finish();
            } catch (JSONException e){

            }

        }
    }


}
