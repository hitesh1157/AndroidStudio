package com.example.hiteshkr.test1;

/**
 * Created by hiteshkr on 14/03/17.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hiteshkr on 13/03/17.
 */

public class SearchRequestPhysics extends StringRequest{

    private static final String PHYSICS_REQUEST_URL = "https://160298.000webhostapp.com/SearchP.php";
    private Map<String, String> params;

    public SearchRequestPhysics(String tag, String physics, Response.Listener<String> listener){

        super(Request.Method.POST, PHYSICS_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("tag", tag);
        params.put("topic", physics);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
