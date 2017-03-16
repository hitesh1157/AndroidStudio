package com.example.hiteshkr.test1;

/**
 * Created by hiteshkr on 14/03/17.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hiteshkr.test1.ChildRow;
import com.example.hiteshkr.test1.ParentRow;
import com.example.hiteshkr.test1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.name;

/**
 * Created by user on 2/27/16.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ParentRow> parentRowList;
    private ArrayList<ParentRow> originalList;

    public MyExpandableListAdapter(Context context
            , ArrayList<ParentRow> originalList) {
        this.context = context;
        this.parentRowList = new ArrayList<>();
        this.parentRowList.addAll(originalList);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(originalList);
    }

    @Override
    public int getGroupCount() {
        return parentRowList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parentRowList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentRowList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentRowList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentRow parentRow = (ParentRow) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_row, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.parent_text);

        heading.setText(parentRow.getName().trim());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildRow childRow = (ChildRow) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row, null);
        }

        ImageView childIcon = (ImageView) convertView.findViewById(R.id.child_icon);
        childIcon.setImageResource(childRow.getIcon());

        final TextView childText = (TextView) convertView.findViewById(R.id.child_text);
        childText.setText(childRow.getText().trim());

        final TextView childId = (TextView) convertView.findViewById(R.id.child_id);
        childId.setText("id: " + childRow.getId().trim());

        final View finalConvertView = convertView;
        childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final int queryid = Integer.parseInt(childRow.getId());
                final int userid = Integer.parseInt(childRow.getuserId());


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                final String topic = jsonResponse.getString("topic");
                                final String subject = jsonResponse.getString("subject");

                                Response.Listener<String> responseListener2 = new Response.Listener<String>(){

                                    @Override
                                    public void onResponse(String response) {


                                        String tom1="";
                                        String ci1="";
                                        String tagst = "";
                                        String qid = "";

                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            if(success)
                                            {
                                                JSONArray jsonArray = jsonResponse.getJSONArray("data");


                                                int length = jsonArray.length();

                                                for(int i=0; i<length; i++){

                                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                                    tagst = tagst + jsonObject.getString("tag") + ",";

                                                    tom1 = jsonObject.getString("tom");
                                                    ci1 = jsonObject.getString("ci");
                                                    qid = qid + jsonObject.getString("query_id") + " ";
                                                }

                                                final String tags = tagst;
                                                final String tom = tom1;
                                                final String ci = ci1;

                                                Intent newIntent = new Intent(context, AddQuery.class);
                                                newIntent.putExtra("user_id", userid);
                                                newIntent.putExtra("subject", subject);
                                                newIntent.putExtra("topic", topic);
                                                newIntent.putExtra("tom", tom);
                                                newIntent.putExtra("ci", ci);
                                                newIntent.putExtra("tags", tags);
                                                newIntent.putExtra("query_id", qid);
                                                newIntent.putExtra("length", length);
                                                context.startActivity(newIntent);


                                            }else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                builder.setMessage("Failed")
                                                        .setNegativeButton("Retry", null)
                                                        .create()
                                                        .show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                };

                                SearchAllTopic getAllRequest = new SearchAllTopic(userid, topic, subject, responseListener2);
                                RequestQueue queue = Volley.newRequestQueue(context);
                                queue.add(getAllRequest);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Not enough.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                EditQueryRequest editQueryRequest = new EditQueryRequest(queryid, responseListener);
                RequestQueue queue2 = Volley.newRequestQueue(context);
                queue2.add(editQueryRequest);


            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query) {
        query = query.toLowerCase();
        parentRowList.clear();

        if (query.isEmpty()) {
            parentRowList.addAll(originalList);
        }
        else {
            for (ParentRow parentRow : originalList) {
                ArrayList<ChildRow> childList = parentRow.getChildList();
                ArrayList<ChildRow> newList = new ArrayList<ChildRow>();

                for (ChildRow childRow: childList) {
                    if (childRow.getText().toLowerCase().contains(query)) {
                        newList.add(childRow);
                    }
                } // end for (com.example.user.searchviewexpandablelistview.ChildRow childRow: childList)
                if (newList.size() > 0) {
                    ParentRow nParentRow = new ParentRow(parentRow.getName(), newList);
                    parentRowList.add(nParentRow);
                }
            } // end or (com.example.user.searchviewexpandablelistview.ParentRow parentRow : originalList)
        } // end else

        notifyDataSetChanged();
    }
}
