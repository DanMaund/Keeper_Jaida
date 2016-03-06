package com.jaida.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseGroupMembers extends AppCompatActivity {

    private static final int CONTACT_PICKER_RESULT = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group_members);
        populateUserList();

        Button addFriendButton = (Button) findViewById(R.id.cgm_add_friend);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), AddFromFacebook.class);
                startActivity(launchSettingsIntent);
            }
        });

        Button confirmGroupButton = (Button) findViewById(R.id.cgm_confirm_group);
        confirmGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), AddMatchType.class);
                startActivity(launchSettingsIntent);            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_group_members, menu);
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

    public void populateUserList(){

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Jamie");
        arrayList.add("Dan");
        arrayList.add("Anton");
        arrayList.add("Wayne");

        ArrayAdapter<String>  ListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                arrayList);

        ListView lv = (ListView) findViewById(R.id.cgm_all_users_list_view);
        lv.setAdapter(ListAdapter);
    }

}
