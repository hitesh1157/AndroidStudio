package com.example.hiteshkr.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_search);


        Intent intent = getIntent();
        final int user_id = intent.getIntExtra("user_id", -1);

        final Button bSearch = (Button) findViewById(R.id.bSaveQuery);
        final EditText etSubject = (EditText) findViewById(R.id.etSubject);
        final EditText etTopic = (EditText) findViewById(R.id.etTopic);
        final EditText etTOM = (EditText) findViewById(R.id.etTOM);
        final EditText etCI = (EditText) findViewById(R.id.etCI);
        final EditText etTags = (EditText) findViewById(R.id.etTags);



        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String subject = etSubject.getText().toString();
                final String topic = etTopic.getText().toString();
                final String tom = etTOM.getText().toString();
                final String ci = etCI.getText().toString();
                final String tagString = etTags.getText().toString();
                String tags = tagString.replace(","," ");
                final String[] words = tags.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    // You may want to check for a non-word character before blindly
                    // performing a replacement
                    // It may also be necessary to adjust the character class
                    words[i] = words[i].replaceAll("[^\\w]", "");
                }







            }
        });


    }
}
