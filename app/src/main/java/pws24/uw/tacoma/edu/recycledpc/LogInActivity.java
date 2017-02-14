package pws24.uw.tacoma.edu.recycledpc;

import android.content.Intent;
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

public class LogInActivity extends AppCompatActivity implements RegisterFragment.RegisterListener{

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static final String LOGIN_URL = "http://cssgate.insttech.washington.edu/~apanlili/login.php?";
    private EditText etEmail;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);

    }

    public void checkLogin(View arg0) {

        String url = buildLogInURL();
        Log.d("WAwa",url);
        new AsyncLogin().execute(url);
    }

    public void insertEmail(String email){
        EditText myTextBox = (EditText) findViewById(R.id.email);
        myTextBox.setText(email);
    }

    public void checkRegister(View arg0) {
        RegisterFragment registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_log_in, registerFragment)
                .addToBackStack(null)
                .commit();
    }

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

        return sb.toString();
    }

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
            Log.d("wawa", result);
            try {
                JSONObject jsonObject = new JSONObject(result);//Why isnt this string converting into json object?
                String status = (String) jsonObject.get("result");
                Log.d("wawa", status);
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Login Successful"
                            , Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    startActivity(intent);
                    LogInActivity.this.finish();
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


}
