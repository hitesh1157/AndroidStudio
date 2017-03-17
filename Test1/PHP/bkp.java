try{
    JSONObject jsonObj = new JSONObject(response);
    boolean success = jsonObj.getBoolean("success");

     if(success) {

         JSONArray Data = jsonObj.getJSONArray("data");
         etName.setText(newTag);
         int length = Data.length();

         for (int i = 0; i < length; i++) {

             JSONObject jsonObj2 = Data.getJSONObject(i);
             newTag = jsonObj2.getString("tag") + " ";
             query_id = jsonObj2.getString("query_id") + " ";
             subject = jsonObj2.getString("subject") + " ";

         }

         Intent intentMain = new Intent(UserAreaActivity.this, MainActivity.class);
         intentMain.putExtra("newTag", newTag);
         intentMain.putExtra("query_id", query_id);
         intentMain.putExtra("subject", subject);
         UserAreaActivity.this.startActivity(intentMain);

     }

}catch(Exception e)
{
}
}
};
