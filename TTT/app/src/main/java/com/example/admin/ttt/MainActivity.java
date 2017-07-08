package com.example.admin.ttt;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    public static String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.admin.ttt",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//        }
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "sc");
                Log.d(TAG, loginResult.getAccessToken().getToken() +" "+  loginResult.getAccessToken().getUserId());


                loginButton.setReadPermissions(Arrays.asList(
                        "public_profile", "email", "user_birthday", "user_friends","user_photos","user_education_history"));

                AccessToken.getCurrentAccessToken().getPermissions();


                final GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                // Application code
                                String name = object.optString(getString(R.string.name));
                                String id = object.optString(getString(R.string.id));
                                String gender = object.optString(getString(R.string.gender));
                                String email = object.optString(getString(R.string.email));
                                String link = object.optString(getString(R.string.link));
                                String birthday = object.optString(getString(R.string.birthday));
//                                JSONObject coverObject = null;
//                                coverObject = object.optJSONObject("cover");
//                                String coverPhoto = null;
//                                try {
//                                    coverPhoto = coverObject.getString("source");
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                Log.d("URL cover photo: ", coverPhoto);
//                                Log.d(TAG, coverPhoto);

                                try {

                                    ArrayList<String> listdata = new ArrayList<String>();
                                    JSONArray jArray = object.getJSONArray("education");
                                    if (jArray != null) {
                                        for (int i=0;i<jArray.length();i++){
                                            listdata.add(jArray.getString(i));
                                            Education education = new Gson().fromJson(jArray.getString(i), Education.class);
                                            Log.d(TAG, education.toString()+ "hihi");
                                            Log.d(TAG, education.getSchool().getName()+"name trường =))) ");
                                            Log.d(TAG, listdata.get(i).toString());
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                URL imageURL = extractFacebookIcon(id);
                                Log.d("name: ",name);
                                Log.d("id: ",id);
                                Log.d("email: ",email);
                                Log.d("link: ",link);
                                Log.d("gender", gender);
                                Log.d("birthday", birthday);
                                Log.d("imageURL: ",imageURL.toString());

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString(getString(R.string.fields), getString(R.string.fields_name));
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Log.d(TAG,"c");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "f");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public URL extractFacebookIcon(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

}
