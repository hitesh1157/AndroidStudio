package com.example.hiteshkr.test1;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    public interface ActivityConstants {
        public static final int ACTIVITY_1 = 1001;
        public static final int ACTIVITY_2 = 1002;
        public static final int ACTIVITY_3 = 1003;
    }


    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] fruits = {"Keep your face always toward the sunshine—and shadows will fall behind you.","It is always the simple that produces the marvelous.","The world is full of magical things patiently waiting for our wits to grow sharper."};
                Snackbar.make(view, fruits[new Random().nextInt(fruits.length)], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();

        // The app will crash if display list is not called here.
        displayList();

        // This expands the list.
        expandAll();


    }



    private void loadData() {


        Intent newIntent = getIntent();
        final String user_id = newIntent.getStringExtra("user_id");
        final String tags = newIntent.getStringExtra("tags");
        final String subjects = newIntent.getStringExtra("subject");
        final String query_ids = newIntent.getStringExtra("query_id");




        final String[] tag = tags.split("\\s+");
        for (int i = 0; i < tag.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            tag[i] = tag[i].replaceAll("[^\\w]", "");
        }

        final String[] subject = subjects.split("\\s+");
        for (int i = 0; i < subject.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            subject[i] = subject[i].replaceAll("[^\\w]", "");
        }

        final String[] query_id = query_ids.split("\\s+");
        for (int i = 0; i < query_id.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            query_id[i] = query_id[i].replaceAll("[^\\w]", "");
        }

        int length = tag.length;


        ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
        ParentRow parentRow = null;





        for(int i=0; i<length; i++)
        {
            if((subject[i].length() >= 3)&&((subject[i].contains("phy"))|| (subject[i].contains("Phy")))){
                childRows.add(new ChildRow(R.drawable.ic_label_outline_black_24dp, tag[i], query_id[i], user_id));
        }
        }
        parentRow = new ParentRow("Physics", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        for(int i=0; i<length; i++)
        {
            if((subject[i].length() >= 3)&&((subject[i].contains("che"))|| (subject[i].contains("Chem"))||(subject[i].contains("Chm"))||(subject[i].contains("chm")))){
                childRows.add(new ChildRow(R.drawable.ic_label_outline_black_24dp, tag[i], query_id[i], user_id));
            }
        }
        parentRow = new ParentRow("Chemistry", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        for(int i=0; i<length; i++)
        {
            if((subject[i].length() >= 3)&&((subject[i].contains("math"))|| (subject[i].contains("Math"))||(subject[i].contains("Mth"))||(subject[i].contains("mth")))){
                childRows.add(new ChildRow(R.drawable.ic_label_outline_black_24dp, tag[i], query_id[i], user_id));
            }
        }
        parentRow = new ParentRow("Mathematics", childRows);
        parentList.add(parentRow);





    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        } //end for (int i = 0; i < count; i++)
    }

    private void displayList() {
        loadData();

        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(MainActivity.this, parentList);

        myList.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo
                (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }
}
