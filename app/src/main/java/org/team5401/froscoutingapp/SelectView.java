package org.team5401.froscoutingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectView extends AppCompatActivity {

    private Spinner viewType;
    private TextView viewName;

    private ArrayList<String> listArray;
    private ListView list;
    private ArrayAdapter adapter;

    private String type, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewType = (Spinner) findViewById(R.id.view_type);
        viewName = (TextView) findViewById(R.id.view_name);

        listArray = new ArrayList<String>();
        list = (ListView) findViewById(R.id.multiple_choice);
        adapter = adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listArray);
        list.setAdapter(adapter);
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }
        //return super.OnOptionsItemSelected(item);
        return false;
    }

    private void readData () {
        type = viewType.getSelectedItem().toString();
        name = viewName.getText().toString();
    }

    public void viewApply (View view) {
        readData();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("type", type);
        resultIntent.putExtra("name", name);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void addMC (View view) {
        startActivityForResult(new Intent(this, MCAnswer.class), 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String name = data.getStringExtra("name");
                String type = data.getStringExtra("type");
                //viewsArray.add(new InputData(name, type));
                adapter.notifyDataSetChanged();
            }
        }
    }
}
