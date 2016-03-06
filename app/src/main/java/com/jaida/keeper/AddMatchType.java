package com.jaida.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddMatchType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_match_type);
        addItemsOnWinConditionsSpinner();
        addItemsOnScoreViewSpinner();
        addItemsOnTimeSpinner();

        Button confirmCompetitorsButton = (Button) findViewById(R.id.amt_conf_activity);
        confirmCompetitorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), Match.class);
                startActivity(launchSettingsIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_match_type, menu);
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

    public void addItemsOnWinConditionsSpinner() {

        Spinner wc_spinner = (Spinner) findViewById(R.id.amt_win_condition_spinner);
        List<String> list = new ArrayList<String>();

        //list.add("Best Of");
        //list.add("First To");
        //list.add("First Past");
        list.add("Most Points");
        list.add("Least Points");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        wc_spinner.setAdapter(dataAdapter);

    }

    public void addItemsOnScoreViewSpinner() {

        Spinner sv_spinner = (Spinner) findViewById(R.id.amt_score_view_spinner);
        List<String> list = new ArrayList<String>();

        list.add("History Mode");
        list.add("Default Mode");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sv_spinner.setAdapter(dataAdapter);

    }

    public void addItemsOnTimeSpinner() {

        Spinner time_spinner = (Spinner) findViewById(R.id.amt_num_rounds_spinner);
        List<String> list = new ArrayList<String>();

        list.add("Max Rounds");
        list.add("Max Time");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        time_spinner.setAdapter(dataAdapter);

    }
}


