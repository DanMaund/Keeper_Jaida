package com.jaida.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MatchSelection extends AppCompatActivity {

    Spinner game_spinner, group_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_selection);
        addItemsOnGameSpinner();

        Button playButton = (Button) findViewById(R.id.match_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), Match.class);
                startActivity(launchSettingsIntent);
            }
        });

        ImageButton addActivityButton = (ImageButton) findViewById(R.id.add_new_activity);
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), AddMatchType.class);
                startActivity(launchSettingsIntent);
            }
        });

        populateTeams();
    }

    public void addItemsOnGameSpinner() {

        game_spinner = (Spinner) findViewById(R.id.game_spinner2);
        List<String> list = new ArrayList<String>();

        list.add("FOOZ");
        list.add("SCRABBLE");
        list.add("CATAN");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        game_spinner.setAdapter(dataAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.match_selection_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_add_user:
                Intent launchAddUserIntent = new Intent(this, AddUser.class);
                startActivity(launchAddUserIntent);
                return true;
            case R.id.action_add_team:

                Intent userManualEntryIntent = new Intent(this, UserManualEntry.class);
                startActivity(userManualEntryIntent);
//                Intent launchAddTeamIntent = new Intent(this, Match.class);
//                startActivity(launchAddTeamIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


   public void populateTeams(){

       ArrayList<String> arrayList = new ArrayList<String>();
       ArrayList<Integer> imageArrayList = new ArrayList<Integer>();
       arrayList.add("jat");
       arrayList.add("Danton");

       imageArrayList.add(R.drawable.user);
       imageArrayList.add(R.drawable.two_user);


       CustomAdapter teamListAdapter = new CustomAdapter(this, arrayList, imageArrayList);

       ListView listView = (ListView) findViewById(R.id.team_members_list_view);
       // Get a reference to the ListView, and attach this adapter to it.
       //listView.setAdapter(mTeamAdapter);
       listView.setAdapter(teamListAdapter);

   }


}