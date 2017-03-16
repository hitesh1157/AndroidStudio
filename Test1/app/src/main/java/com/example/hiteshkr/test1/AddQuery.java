package com.example.hiteshkr.test1;

import java.lang.String;
import java.util.*;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





public class AddQuery extends AppCompatActivity {

    ImageView imageView;
    int i;

    public interface ActivityConstants {
        public static final int ACTIVITY_1 = 1001;
        public static final int ACTIVITY_2 = 1002;
        public static final int ACTIVITY_3 = 1003;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_query);




        Intent intent = getIntent();

/*        final boolean success = intent.getBooleanExtra("success", false);*/
        final String name = intent.getStringExtra("name");
        final String username = intent.getStringExtra("username");

        final int user_id = intent.getIntExtra("user_id", -1);
        final int callAct = intent.getIntExtra("calling_act",0);
        final String subject = intent.getStringExtra("subject");
        final String topic = intent.getStringExtra("topic");
        final String tom = intent.getStringExtra("tom");
        final String ci = intent.getStringExtra("ci");
        final String tags = intent.getStringExtra("tags");
        final String qid = intent.getStringExtra("query_id");
        final int length = intent.getIntExtra("length", -1);


        final EditText etSubject = (EditText) findViewById(R.id.etSubject);
        final EditText etTopic = (EditText) findViewById(R.id.etTopic);
        final EditText etTOM = (EditText) findViewById(R.id.etTOM);
        final EditText etCI = (EditText) findViewById(R.id.etCI);
        final EditText etTags = (EditText) findViewById(R.id.etTags);

        final Button bSave = (Button) findViewById(R.id.bSaveQuery);
        final Button bCapture = (Button) findViewById(R.id.bCapture);
        imageView = (ImageView) findViewById(R.id.imageView);

        etSubject.setText(subject);
        etTopic.setText(topic);
        etTOM.setText(tom);
        etCI.setText(ci);
        etTags.setText(tags);



        bCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 0);
                }
            }

        });


        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(callAct != ActivityConstants.ACTIVITY_1) {

                    String qidE = qid.replace(","," ");
                    final String[] query_id = qidE.split("\\s+");
                    for (int i = 0; i < query_id.length; i++) {
                        // You may want to check for a non-word character before blindly
                        // performing a replacement
                        // It may also be necessary to adjust the character class
                        query_id[i] = query_id[i].replaceAll("[^\\w]", "");
                    }


                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
/*
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddQuery.this);
                                builder.setMessage("Deleting row...")
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    for(int i=0; i<query_id.length; i++) {
                        DeleteRow deleteRow = new DeleteRow(Integer.parseInt(query_id[i]), user_id, responseListener2);
                        RequestQueue queue2 = Volley.newRequestQueue(AddQuery.this);
                        queue2.add(deleteRow);

                    }

                }

                final String subject = etSubject.getText().toString();
                final String topic = etTopic.getText().toString();
                final String tom = etTOM.getText().toString();
                final String ci = etCI.getText().toString();
                final String tagString = etTags.getText().toString();
                String tags = tagString.replace(","," ");
                final String[] words = tags.split("\\s+");
                for (i = 0; i < words.length; i++) {
                    // You may want to check for a non-word character before blindly
                    // performing a replacement
                    // It may also be necessary to adjust the character class
                    words[i] = words[i].replaceAll("[^\\w]", "");
                }

                final String MTags = tags;
                final String userID = Integer.toString(user_id);



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                if(callAct == ActivityConstants.ACTIVITY_1) {
                                    Intent intent = new Intent(AddQuery.this, UserAreaActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("username", username);
                                    intent.putExtra("user_id", user_id);
                                    AddQuery.this.startActivity(intent);
                                }
                                else{
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

                                                    Intent newIntent = new Intent(AddQuery.this, MainActivity.class);
                                                    newIntent.putExtra("user_id",Integer.toString(user_id));
                                                    newIntent.putExtra("tags", tags);
                                                    newIntent.putExtra("subject", subject);
                                                    newIntent.putExtra("query_id", query_id);
                                                    AddQuery.this.startActivity(newIntent);



                                                }else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddQuery.this);
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
                                    RequestQueue queue = Volley.newRequestQueue(AddQuery.this);
                                    queue.add(getAllRequest);






























                                }

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddQuery.this);
                                builder.setMessage("Query Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

/*                if(callAct != ActivityConstants.ACTIVITY_1) {

                    String qidE = qid.replace(","," ");
                    final String[] query_id = qidE.split("\\s+");
                    for (int i = 0; i < query_id.length; i++) {
                        // You may want to check for a non-word character before blindly
                        // performing a replacement
                        // It may also be necessary to adjust the character class
                        query_id[i] = query_id[i].replaceAll("[^\\w]", "");
                    }


                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    for(int i=0; i<query_id.length; i++) {
                            DeleteRow deleteRow = new DeleteRow(Integer.parseInt(query_id[i]), user_id, responseListener2);
                            RequestQueue queue2 = Volley.newRequestQueue(AddQuery.this);
                            queue2.add(deleteRow);

                    }



                    for (i = 0; i < words.length; i++) {
                        QueryRequest queryRequest = new QueryRequest(user_id, subject, topic, tom, ci, words[i], responseListener);
                        RequestQueue queue3 = Volley.newRequestQueue(AddQuery.this);
                        queue3.add(queryRequest);
                    }


                }*/

                    for (i = 0; i < words.length; i++) {
                        QueryRequest queryRequest = new QueryRequest(user_id, subject, topic, tom, ci, words[i], responseListener);
                        RequestQueue queue4 = Volley.newRequestQueue(AddQuery.this);
                        queue4.add(queryRequest);
                    }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 0);
            }
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }

    }
}


