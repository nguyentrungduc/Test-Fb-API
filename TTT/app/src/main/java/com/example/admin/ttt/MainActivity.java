package com.example.admin.ttt;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

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
                        "public_profile", "email", "user_birthday","user_about_me", "user_friends","user_photos","user_education_history","user_work_history",
                        "user_posts","read_custom_friendlists","user_friends","user_likes"));

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
                                    Log.d(TAG, object.toString());




//                                try {
//                                    JSONArray jsonArray = object.getJSONObject("photos").getJSONArray("data");
//                                    Log.d(TAG, jsonArray.toString());
//                                    ArrayList<String> listdata = new ArrayList<String>();
//                                    for(int i = 0; i  < jsonArray.length(); i ++){
//                                        listdata.add(jsonArray.getString(i));
//                                            Photo photo = new Gson().fromJson(jsonArray.getString(i), Photo.class);
//                                            Log.d(TAG, photo.getId().toString()+"   id =)))");
//                                        new GraphRequest(
//                                                AccessToken.getCurrentAccessToken(),
//                                                "/"+photo.getId().toString()+"/picture",
//                                                null,
//                                                HttpMethod.GET,
//                                                new GraphRequest.Callback() {
//                                                    public void onCompleted(GraphResponse response) {
//                                                        Log.d(TAG, response.getConnection().toString());
//
//                                                    }
//                                                }
//                                        ).executeAsync();
//
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }

                                try {
                                    Log.d(TAG, object.getJSONObject("albums").getJSONObject("paging").getJSONObject("cursors").optString("after").toString()+"aaaa");
                                    Log.d(TAG, object.getJSONObject("albums").getJSONArray("data").toString() +" albums");
                                    JSONArray jsonArray = object.getJSONObject("albums").getJSONArray("data");
                                    ArrayList<String> listdata = new ArrayList<String>();
                                    for(int i = 0; i  < jsonArray.length(); i ++){
                                        listdata.add(jsonArray.getString(i));
                                        Album album = new Gson().fromJson(jsonArray.getString(i), Album.class);
                                        Log.d(TAG, album.getId().toString()+"   id =)))");
                                        new GraphRequest(
                                                AccessToken.getCurrentAccessToken(),
                                                "/"+album.getId()+"/photos",
                                                null,
                                                HttpMethod.GET,
                                                new GraphRequest.Callback() {
                                                    public void onCompleted(GraphResponse response) {
                                                       // Log.d(TAG, response.getConnection().toString()+"hihi");
                                                        try {
                                                            Log.d(TAG, response.getJSONObject().getJSONArray("data").toString());
                                                            JSONArray jsonArray1 = response.getJSONObject().getJSONArray("data");
                                                            ArrayList arrayList = new ArrayList<String>();
                                                            for(int i = 0; i < jsonArray1.length(); i ++){
                                                                Photo photo = new Gson().fromJson(jsonArray1.getString(i), Photo.class);
                                                                Log.d(TAG, photo.getId()+"id=))))");
                                                                new GraphRequest(
                                                                        AccessToken.getCurrentAccessToken(),
                                                                        "/"+photo.getId().toString()+"/picture",
                                                                        null,
                                                                        HttpMethod.GET,
                                                                        new GraphRequest.Callback() {
                                                                            public void onCompleted(GraphResponse response) {
                                                                                Log.d(TAG, response.getConnection().toString());

                                                                            }
                                                                        }
                                                                ).executeAsync();
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }
                                        ).executeAsync();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                final String[] afterString = {""};  // will contain the next page cursor
                                final Boolean[] noData = {false};   // stop when there is no after cursor
                                do {
                                    Bundle params = new Bundle();
                                    params.putString("after", afterString[0]);
                                    new GraphRequest(
                                            AccessToken.getCurrentAccessToken(),
                                            object.optString("id") + "/likes",
                                            params,
                                            HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                @Override
                                                public void onCompleted(GraphResponse graphResponse) {
                                                    Log.d(TAG, graphResponse.toString());
                                                    JSONObject jsonObject = graphResponse.getJSONObject();
                                                    try {
                                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                                        //  your code


                                                        if(!jsonObject.isNull("paging")) {
                                                            JSONObject paging = jsonObject.getJSONObject("paging");
                                                            JSONObject cursors = paging.getJSONObject("cursors");
                                                            if (!cursors.isNull("after"))
                                                                afterString[0] = cursors.getString("after");
                                                            else
                                                                noData[0] = true;
                                                        }
                                                        else
                                                            noData[0] = true;
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                    ).executeAndWait();
                                }
                                while(!noData[0] == true);
//
//                                try {
//
//                                    ArrayList<String> listdata = new ArrayList<String>();
//                                    JSONArray jArray = object.getJSONArray("education");
//                                    if (jArray != null) {
//                                        for (int i=0;i<jArray.length();i++){
//                                            listdata.add(jArray.getString(i));
//                                            Education education = new Gson().fromJson(jArray.getString(i), Education.class);
//                                            Log.d(TAG, education.toString()+ "hihi");
//                                            Log.d(TAG, education.getSchool().getName()+"name trường =))) ");
//                                            Log.d(TAG, listdata.get(i).toString());
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }

//                                URL imageURL = extractFacebookIcon(id);
//                                Log.d("name: ",name);
//                                Log.d("id: ",id);
//                                Log.d("email: ",email);
//                                Log.d("link: ",link);
//                                Log.d("gender", gender);
//                                Log.d("birthday", birthday);
//                                Log.d("imageURL: ",imageURL.toString());
                                try {
                                    Log.d(TAG, object.getJSONObject("friends").toString() +"aa" );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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
