package com.example.hiteshkr.test1;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class UserAreaActivity extends AppCompatActivity {



    public interface ActivityConstants {
        public static final int ACTIVITY_1 = 1001;
        public static final int ACTIVITY_2 = 1002;
        public static final int ACTIVITY_3 = 1003;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);



        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String username = intent.getStringExtra("username");
        final int userID = intent.getIntExtra("user_id", -1);


        final TextView etName = (TextView) findViewById(R.id.etName);
        // Display user details
        etName.setText(name);

        final Button bLogout = (Button) findViewById(R.id.bLogout);
        final Button bQuery = (Button) findViewById(R.id.bQuery);
        final Button bDelete = (Button) findViewById(R.id.bDelete);
        final Button bSearch = (Button) findViewById(R.id.bSearch);
        final Button bCSearch = (Button) findViewById(R.id.bCSearch);
        final int user_id = userID;

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bCSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cs = new Intent(UserAreaActivity.this, CustomSearch.class);
                cs.putExtra("user_id", userID);
                UserAreaActivity.this.startActivity(cs);
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentLogout = new Intent(UserAreaActivity.this, LoginActivity.class);
                intentLogout.putExtra("username", username);
                UserAreaActivity.this.startActivity(intentLogout);
            }
        });

        bQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intentQuery = new Intent(UserAreaActivity.this, AddQuery.class);
                intentQuery.putExtra("name", name);
                intentQuery.putExtra("username", username);
                intentQuery.putExtra("user_id", user_id);
                intentQuery.putExtra("calling_act", ActivityConstants.ACTIVITY_1);
                UserAreaActivity.this.startActivity(intentQuery);

            }
        });



        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {


                        String tagst = "";
                        String subjects = "";
                        String query_idq = "";
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success)
                            {
                                JSONArray jsonArray = jsonResponse.getJSONArray("data");


                                int length = jsonArray.length();

                                for(int i=0; i<length; i++){

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    tagst = tagst + " " + jsonObject.getString("tag");
                                    subjects = subjects + " " + jsonObject.getString("subject");
                                    query_idq = query_idq + " " + jsonObject.getString("query_id");

                                }

                                final String tags = tagst;
                                final String subject = subjects;
                                final String query_id = query_idq;

                                Intent newIntent = new Intent(UserAreaActivity.this, MainActivity.class);
                                newIntent.putExtra("user_id",Integer.toString(user_id));
                                newIntent.putExtra("tags", tags);
                                newIntent.putExtra("subject", subject);
                                newIntent.putExtra("query_id", query_id);
                                UserAreaActivity.this.startActivity(newIntent);



                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                                builder.setMessage("No Query added.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                GetAllRequest getAllRequest = new GetAllRequest(user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(getAllRequest);


            }
        });


    }
}
