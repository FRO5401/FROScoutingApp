package org.team5401.froscoutingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class CreateScoutingInput extends AppCompatActivity {

    ListView list;
    ArrayAdapter adapter;
    ArrayList<InputData> viewsArray;
    Boolean deleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_scouting_input);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.view_list);

        deleteMode = false;

        viewsArray = new ArrayList<InputData>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewsArray);
        list.setAdapter(adapter);

        SharedPreferences scoutingInput = getSharedPreferences("scoutingInput", MODE_PRIVATE);
        Gson gson = new Gson();
        for (int i = 0; i < scoutingInput.getAll().size(); i++) {
            String json = scoutingInput.getString(valueOf(i), "");
            InputData data = gson.fromJson(json, InputData.class);
            viewsArray.add(data);
        }
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

    public void add (View view) {
        startActivityForResult(new Intent(this, SelectView.class), 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String name = data.getStringExtra("name");
                String type = data.getStringExtra("type");
                viewsArray.add(new InputData(name, type));
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void delete (View view) {
        deleteMode = true;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (deleteMode) {
                    viewsArray.remove(position);
                    adapter.notifyDataSetChanged();
                    deleteMode = false;
                }
            }
        });
    }

    public void save (View view) {
        SharedPreferences scoutingInput = getSharedPreferences("scoutingInput", MODE_PRIVATE);
        SharedPreferences.Editor editor = scoutingInput.edit();
        Gson gson = new Gson();
        editor.clear();
        for (int i = 0; i < viewsArray.size(); i++) {
            String json = gson.toJson(viewsArray.get(i));
            editor.putString(valueOf(i), json);
        }
        editor.apply();
        Snackbar.make(findViewById(R.id.constraintLayout), "Layout Applied", Snackbar.LENGTH_SHORT).show();
    }
}
