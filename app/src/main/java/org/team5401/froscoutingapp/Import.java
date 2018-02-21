package org.team5401.froscoutingapp;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageDirectory;
import static java.lang.String.valueOf;

public class Import extends AppCompatActivity {

    ArrayList<String> layouts;
    ListView list;
    ArrayAdapter adapter;
    String[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layouts = new ArrayList<String>();

        File dir = new File(getExternalStorageDirectory(), "/FROScoutingApp/InputLayouts");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                layouts.add(file.getName());
            }
        } else {
            layouts.add("No layouts found.layout");
        }
        int i = 0;
        for (String name : layouts) {
            name = name.substring(0, name.lastIndexOf("."));
            layouts.set(i, name);
            i++;
        }

        list = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, layouts);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {
                    importLayout(position);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
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

    public void importLayout (int position) throws IOException {
        String layout = layouts.get(position);
        FileReader reader = new FileReader(getExternalStorageDirectory() + "/FROScoutingApp/InputLayouts/" + layout + ".layout");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String text = bufferedReader.readLine();
        String[] inputLayouts = text.split(",");
        ArrayList<InputData> viewsArray = new ArrayList<InputData>();
        for (int i = 0; i < inputLayouts.length; i += 2) {
            viewsArray.add(new InputData(inputLayouts[i], inputLayouts[i+1]));
        }

        SharedPreferences scoutingInput = getSharedPreferences("scoutingInput", MODE_PRIVATE);
        SharedPreferences.Editor editor = scoutingInput.edit();
        Gson gson = new Gson();
        editor.clear();
        for (int i = 0; i < viewsArray.size(); i++) {
            String json = gson.toJson(viewsArray.get(i));
            editor.putString(valueOf(i), json);
        }
        editor.apply();

        String snackbarText = "Imported layout: " + layout;
        Snackbar.make(findViewById(R.id.constraintLayout), snackbarText, Snackbar.LENGTH_SHORT).show();
    }
}
