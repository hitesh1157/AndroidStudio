package com.example.hiteshkr.test1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hiteshkr on 13/03/17.
 */

public class getQueue extends StringRequest{

    private static final String GET_REQUEST_URL = "https://160298.000webhostapp.com/Queue.php";
    private Map<String, String> params;

    public getQueue(String topic, Response.Listener<String> listener){

        super(Request.Method.POST, GET_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("topic", topic);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
