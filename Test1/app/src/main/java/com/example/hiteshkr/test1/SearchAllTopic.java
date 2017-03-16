package com.example.hiteshkr.test1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hiteshkr on 13/03/17.
 */

public class SearchAllTopic extends StringRequest {

    private static final String SEARCH_ALL_REQUEST_URL = "https://160298.000webhostapp.com/SearchAllTopic.php";
    private Map<String, String> params;

    public SearchAllTopic(int user_id, String topic, String subject, Response.Listener<String> listener) {

        super(Request.Method.POST, SEARCH_ALL_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_id", user_id + "");
        params.put("topic", topic);
        params.put("subject", subject);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}