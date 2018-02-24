package org.team5401.froscoutingapp;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;
import static java.lang.String.valueOf;

public class Scouting extends AppCompatActivity {

    ArrayList<InputData> viewsInputDataArray;
    ArrayList<DataHolder> viewsArray;
    String[][] dataArray;

    //LinearLayout linearName, linearInput;
    EditText matchInput, robotInput;
    String match, robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewsInputDataArray = new ArrayList<InputData>();
        viewsArray = new ArrayList<DataHolder>();



        //linearName = (LinearLayout) findViewById(R.id.linear_name);
        //linearInput = (LinearLayout) findViewById(R.id.linear_input);
        matchInput = (EditText) findViewById(R.id.match_input);
        robotInput = (EditText) findViewById(R.id.robot_input);

        SharedPreferences scoutingInput = getSharedPreferences("scoutingInput", MODE_PRIVATE);
        Gson gson = new Gson();
        for (int i = 0; i < scoutingInput.getAll().size(); i++) {
            String json = scoutingInput.getString(valueOf(i), "");
            InputData data = gson.fromJson(json, InputData.class);
            viewsInputDataArray.add(data);
        }

        dataArray = new String[viewsInputDataArray.size()][2];
        importSettings();
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

    public void importSettings () {
        LinearLayout inputs = (LinearLayout) findViewById(R.id.inputs);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

        for (int i = 0; i < viewsInputDataArray.size(); i++) {
            LinearLayout linearWrapper = new LinearLayout(this);
            linearWrapper.setOrientation(LinearLayout.HORIZONTAL);
            linearWrapper.setPadding(0,0,0,20);

            TextView nameView = new TextView(this);
            nameView.setText(viewsInputDataArray.get(i).getName());
            nameView.setLayoutParams(param);
            nameView.setTextSize(COMPLEX_UNIT_SP, 18);
            nameView.setGravity(CENTER_VERTICAL);
            nameView.setPadding(40, 0, 0, 0);
            switch (viewsInputDataArray.get(i).getType()) {
                case "Text Input": {                         //Brackets to create scopes to allow the reuse of variable names
                    EditText inputView = new EditText(this);
                    inputView.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_CAP_SENTENCES);
                    inputView.setLayoutParams(param);

                    linearWrapper.addView(nameView);
                    linearWrapper.addView(inputView);
                    inputs.addView(linearWrapper);

                    DataHolder holder = new DataHolder(inputView);
                    viewsArray.add(holder);
                    break;
                }
                case "Number Input": {
                    EditText inputView = new EditText(this);
                    inputView.setInputType(TYPE_CLASS_NUMBER);
                    inputView.setLayoutParams(param);

                    linearWrapper.addView(nameView);
                    linearWrapper.addView(inputView);
                    inputs.addView(linearWrapper);

                    DataHolder holder = new DataHolder(inputView);
                    viewsArray.add(holder);
                    break;
                }
                case "Switch": {
                    Switch inputView = new Switch(this);
                    inputView.setLayoutParams(param);
                    //inputView.setSwitchMinWidth(100);

                    linearWrapper.addView(nameView);
                    linearWrapper.addView(inputView);
                    inputs.addView(linearWrapper);

                    DataHolder holder = new DataHolder(inputView);
                    viewsArray.add(holder);
                    break;
                }
                case "Multiple Choice": {
                    RadioGroup group = new RadioGroup(this);
                    //RadioButton[] buttons = new RadioButton[arraylength];
                    //for (int j = 0; j < arraylength; j++) {

                    //}
                    break;
                }
                case "Divider": {
                    nameView.setTypeface(null, Typeface.BOLD);
                    nameView.setGravity(CENTER_HORIZONTAL);
                    nameView.setTextSize(COMPLEX_UNIT_SP, 22);
                    nameView.setPadding(0, 30, 0, 16);

                    linearWrapper.addView(nameView);
                    inputs.addView(linearWrapper);
                    break;
                }
                default:
                    System.out.println("m a c h i n e b r o k e");
                    break;
            }
        }
    }

    private void readData () {
        match = matchInput.getText().toString();
        robot = robotInput.getText().toString();
        for (int i = 0; i < viewsArray.size(); i++) {
            //System.out.println("iteration a" + i);
            String name = viewsInputDataArray.get(i).getName();
            String type = viewsInputDataArray.get(i).getType();
            String data = null;
            if (type.equals("Divider")) {
                System.out.println("div skip");
                continue;
            }
            System.out.println(name);
            //System.out.println(type);
            switch (type) {
                case "Text Input":
                case "Number Input":
                    System.out.println("num");
                    data = viewsArray.get(i).getEditText().getText().toString();
                    if (data.equals("")) {
                        data = "0";
                    }
                    break;
                case "Switch":
                    System.out.println("switch");
                    if (viewsArray.get(i).getSwitch().isChecked()) {
                        data = "1";
                    } else if (!viewsArray.get(i).getSwitch().isChecked()) {
                        data = "0";
                    } else {
                        data = "error";
                    }
                    break;
                case "Divider":
                    System.out.println("div");
                    continue;
                    //divider doesn't do anything, this is here to prevent m a c h i n e b r o k e
                    //break;
                default:
                    System.out.println("m a c h i n e b r o k e");
                    break;
            }
            dataArray[i][0] = name;
            dataArray[i][1] = data;
            //System.out.println("iteration b" + i);
        }
    }

    public void saveData (View view) {
        readData();
        SharedPreferences matchRobot = getSharedPreferences("matchRobot", MODE_PRIVATE);
        SharedPreferences.Editor mrEditor = matchRobot.edit();
        mrEditor.putString("match", match);
        mrEditor.putString("robot", robot);
        mrEditor.apply();
        SharedPreferences scoutingNames = getSharedPreferences("scoutingNames", MODE_PRIVATE);
        SharedPreferences.Editor scoutingNamesEditor = scoutingNames.edit();
        SharedPreferences scoutingData = getSharedPreferences("scoutingData", MODE_PRIVATE);
        SharedPreferences.Editor scoutingDataEditor = scoutingData.edit();
        scoutingNamesEditor.clear();
        scoutingDataEditor.clear();
        //Gson gson = new Gson();
        for (int i = 0; i < dataArray.length; i++) {
            //DataHolder holder = new DataHolder(dataArray[i][0], dataArray[i][1]);  //gson causes ram issues
            //String json = gson.toJson(holder);
            //editor.putString(valueOf(i), json);
            scoutingNamesEditor.putString(valueOf(i), dataArray[i][0]);
            scoutingDataEditor.putString(valueOf(i), dataArray[i][1]);
        }
        scoutingNamesEditor.apply();
        scoutingDataEditor.apply();
    }
}
