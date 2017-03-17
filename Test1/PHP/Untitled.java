Response.Listener<String> responseListener = new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {


    }
};




GetAllRequest getAllRequest = new GetAllRequest(user_id, responseListener);
RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
queue.add(getAllRequest);
