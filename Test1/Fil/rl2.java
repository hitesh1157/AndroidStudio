try {
    JSONArray jsonResponse = new JSONArray(response);
    String newTag = "";
    String query_id = "";
    String subject = "";

    for(int i=0; i<jsonResponse.length(); i++){
        JSONObject jsonObject = jsonResponse.getJSONObject(i);

        newTag = jsonObject.getString("tag") + " ";
        query_id = jsonObject.getString("query_id") + " ";
        subject = jsonObject.getString("subject") + " ";
    }

    Intent intentMain = new Intent(UserAreaActivity.this, MainActivity.class);
    intentMain.putExtra("newTag", newTag);
    intentMain.putExtra("query_id", query_id);
    intentMain.putExtra("subject", subject);
    UserAreaActivity.this.startActivity(intentMain);

} catch (JSONException e) {
    e.printStackTrace();
}
