package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.tourismapp.database.SessionManager;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private MaterialCheckBox mRememberCheckBox;
    private TextInputLayout userNameField, passWordField;
    private static final String TAG = "loginactivity::";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        String userData = sessionManager.readUserData();
        if (userData != null && userData.isEmpty()) {
            initializeUserDb();
        }
        mLoginButton = (Button) findViewById(R.id.login);
        userNameField = (TextInputLayout) findViewById(R.id.username);
        passWordField = (TextInputLayout) findViewById(R.id.password);
        mRememberCheckBox = (MaterialCheckBox) findViewById(R.id.rememberMe);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            String userName, passWord;

            @Override
            public void onClick(View view) {
                userName = userNameField.getEditText().getText().toString();
                passWord = passWordField.getEditText().getText().toString();
                if (validateLoginParameters()) {
                    try {
                        JSONObject userData = new JSONObject(sessionManager.readUserData());
                        JSONArray userInfoArray = userData.getJSONArray("userdata");
                        String savedUserName, savedpassword;
                        for (int i = 0; i < userInfoArray.length(); i++) {
                            savedUserName = userInfoArray.getJSONObject(i).getString("username");
                            savedpassword = userInfoArray.getJSONObject(i).getString("password");
                            if (savedUserName.equals(userName) && savedpassword.equals(passWord)) {
                                userInfoArray.getJSONObject(i).put("isLoggedIn", true);
                                userInfoArray.getJSONObject(i).put("rememberMe", mRememberCheckBox.isChecked());
                                sessionManager.setCurrentUserEmail(userInfoArray.getJSONObject(i).getString("username"));
                                sessionManager.setRememberMeStatus(mRememberCheckBox.isChecked());

                                break;
                            } else if (!savedUserName.equals(userName) || !savedpassword.equals(passWord)) {
                                Toast.makeText(LoginActivity.this, "Please provide correct username and password", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this, "User doesnt exist", Toast.LENGTH_SHORT).show();

                            }
                        }
                        userData.put("userdata", userInfoArray);
                        sessionManager.writeUserData(userData.toString());
                        Log.d(TAG, userData.toString());
                        Log.d(TAG,"Successful login");
                        checkUserLoggedIn();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            private boolean validateLoginParameters() {
                if (userName.isEmpty()) {
                    userNameField.setError("Username cannot be empty");
                    return false;
                } else if (passWord.isEmpty()) {
                    userNameField.setError(null);
                    passWordField.setError("password cannot be empty");
                    return false;
                } else {
                    userNameField.setError(null);
                    passWordField.setError(null);
                    return true;
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserLoggedIn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONObject userData = null;
        Log.d(TAG, "Inside on destroy");

        try {
            userData = new JSONObject(sessionManager.readUserData());
            JSONArray userInfoArray = userData.getJSONArray("userdata");

            for (int i = 0; i < userInfoArray.length(); i++) {
                boolean loggedInStatus = userInfoArray.getJSONObject(i).getBoolean("isLoggedIn");


                userInfoArray.getJSONObject(i).put("isLoggedIn", loggedInStatus && sessionManager.getRememberMeStatus());
            }
            userData.put("userdata", userInfoArray);
            sessionManager.writeUserData(userData.toString());
            Log.d(TAG, userData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void checkUserLoggedIn() {
        JSONObject userData;
        boolean isLoggedin = false;
        try {
            userData = new JSONObject(sessionManager.readUserData());
            JSONArray userInfoArray = userData.getJSONArray("userdata");
            for (int i = 0; i < userInfoArray.length() && !isLoggedin; i++) {
                isLoggedin = (boolean) userInfoArray.getJSONObject(i).get("isLoggedIn");
            }
            if (isLoggedin) {
                Log.d(TAG,"Logged in already");
                Intent intent = new Intent(LoginActivity.this, AttractionListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initializeUserDb() {
        JSONArray userInfo = new JSONArray();
        JSONObject user = new JSONObject();
        try {
            user.put("username", "thanos@gmail.com");
            user.put("password", "1234");
            user.put("isLoggedIn", false);
            userInfo.put(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        user = new JSONObject();
        try {
            user.put("username", "wonderwoman@yahoo.com");
            user.put("password", "abc!!");
            user.put("isLoggedIn", false);
            userInfo.put(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject data = new JSONObject();
        try {
            data.put("userdata", userInfo);
            sessionManager.writeUserData(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}