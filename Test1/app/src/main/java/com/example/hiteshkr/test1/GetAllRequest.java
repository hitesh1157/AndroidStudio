package com.example.hiteshkr.test1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hiteshkr on 13/03/17.
 */

public class GetAllRequest extends StringRequest{

    private static final String GET_ALL_REQUEST_URL = "https://160298.000webhostapp.com/GetAll.php";
    private Map<String, String> params;

    public GetAllRequest(int user_id, Response.Listener<String> listener){

        super(Request.Method.POST, GET_ALL_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_id", user_id + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
