package com.example.hiteshkr.test1;


import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by hiteshkr on 13/03/17.
 */

public class QueryRequest extends StringRequest{

    private static final String QUERY_REQUEST_URL = "https://160298.000webhostapp.com/AddQuery2.php";
    private Map<String, String> params;

    public QueryRequest(int user_id, String subject, String topic, String tom, String ci, String tag, Response.Listener<String> listener){

        super(Method.POST, QUERY_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_id", user_id + "");
        params.put("subject", subject);
        params.put("topic", topic);
        params.put("tom", tom);
        params.put("ci", ci);
        params.put("tag", tag);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
