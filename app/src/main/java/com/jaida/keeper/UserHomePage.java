package com.jaida.keeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserHomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        Typeface headerFont = Typeface.createFromAsset(getAssets(), uiUtilities.HEADER_FONT);
        Typeface bodyFont = Typeface.createFromAsset(getAssets(), uiUtilities.BODY_FONT);

        TextView titleText = (TextView) findViewById(R.id.htb_username);
        titleText.setTypeface(headerFont);

        TextView quoteText = (TextView) findViewById(R.id.htb_quote);
        quoteText.setTypeface(bodyFont);

        Button editGroups = (Button) findViewById(R.id.auhp_edit_groups);
        editGroups.setTypeface(bodyFont);
        editGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), MyGroups.class);
                startActivity(launchSettingsIntent);
            }
        });

        Button createGroup = (Button) findViewById(R.id.auhp_create_new_groups);
        createGroup.setTypeface(bodyFont);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), ChooseGroupMembers.class);
                startActivity(launchSettingsIntent);
            }
        });
        populateGroupGrid();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_home_page, menu);
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

    public void populateGroupGrid(){

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("FoozBoiz");
        arrayList.add("HotYoungTings");
        arrayList.add("Work");
        arrayList.add("NewGroup");

        ArrayAdapter<String> GridAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                arrayList);

        Integer[] mThumbIds = {
                R.drawable.add_group, R.drawable.del_button,
                R.drawable.multi_user, R.drawable.two_user};

        int[] rainbow = this.getResources().getIntArray(R.array.androidcolors);
        //Color red = new Color(0xFFFFFF);
        //Color[] colours = {};
        GridViewAdapter gridAdapter = new GridViewAdapter(this, mThumbIds, rainbow);
        GridView gv = (GridView) findViewById(R.id.uhp_group_grid);
        gv.setAdapter(gridAdapter);
    }
}
