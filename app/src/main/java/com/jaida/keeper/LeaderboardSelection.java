package com.jaida.keeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardSelection extends AppCompatActivity {

    Spinner game_spinner, group_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_selection);
        addItemsOnGameSpinner();
        addItemsOnGroupSpinner();
    }

    // add items into spinner dynamically
    public void addItemsOnGameSpinner() {

        game_spinner = (Spinner) findViewById(R.id.game_spinner);
        List<String> list = new ArrayList<String>();

        list.add("FOOZ");
        list.add("SCRABBLE");
        list.add("CATAN");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        game_spinner.setAdapter(dataAdapter);

    }

    // add items into spinner dynamically
    public void addItemsOnGroupSpinner() {

        group_spinner = (Spinner) findViewById(R.id.group_spinner);
        String[] groups = getResources().getStringArray(R.array.sample_groups);

        List<String> list = new ArrayList<String>();

        for(int i = 0; i < groups.length; i++)
        {
            list.add(groups[i]);
        }
        list.add("ALL GROUPS");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        group_spinner.setAdapter(dataAdapter);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leaderboard_selection, menu);
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
}
