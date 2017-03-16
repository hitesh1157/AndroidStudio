package com.example.hiteshkr.test1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hiteshkr on 13/03/17.
 */

public class EditQueryRequest extends StringRequest{

    private static final String EDIT_QUERY_REQUEST_URL = "https://160298.000webhostapp.com/EditQueryRequest.php";
    private Map<String, String> params;

    public EditQueryRequest(int query_id, Response.Listener<String> listener){

        super(Request.Method.POST, EDIT_QUERY_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("query_id", query_id + " ");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
