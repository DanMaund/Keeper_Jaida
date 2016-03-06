package com.jaida.keeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AddUser extends AppCompatActivity {

    private static final int PICK_CONTACT = 1001;
    private static final int CONTACT_PICKER_RESULT = 1002;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Button addUserButton = (Button) findViewById(R.id.add_user_button);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), MatchSelection.class);
                startActivity(launchSettingsIntent);
            }
        });

        addGroupsToSpinner();
        populateUsers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_user_actions, menu);
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
            case R.id.action_add_new_user:
                launchAddNewUserDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchAddNewUserDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.add_a_user)
                .setItems(R.array.add_user_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String[] add_user_options = getResources().getStringArray(R.array.add_user_options);
                        String option = add_user_options[which];
                        switch (which){
                            case 0:
                                Intent userManualEntryIntent = new Intent(getBaseContext(), UserManualEntry.class);
                                startActivity(userManualEntryIntent);
                                dialog.cancel();
                                break;
                            case 1:
                                Intent userManualEntryInten = new Intent(getBaseContext(), UserManualEntry.class);
                                startActivity(userManualEntryInten);
                                dialog.cancel();
                                break;
                            case 2:
                                chooseFromContacts();
                                dialog.cancel();
                                break;
                        }
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void chooseFromContacts() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, CONTACT_PICKER_RESULT);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
//
//            String whereName = ContactsContract.Data.MIMETYPE + " = ?";
//            String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
//
//            Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
//            cursor.moveToFirst();
//            int firstNameCol = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
//            int lastNameCol = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
//
//            String givenName = cursor.getString(firstNameCol);
//            String familyName = cursor.getString(lastNameCol);
//
//            Toast toast = Toast.makeText(getApplicationContext(), (givenName + familyName), Toast.LENGTH_SHORT);
//            toast.show();
//            cursor.close();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    handleContactSelection(data);
                    break;
            }
        }
    }

    private void handleContactSelection(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Cursor cursor = null;
                Cursor nameCursor = null;
                try {
                    cursor = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID} ,
                            null, null, null);

                    String phoneNumber = null;
                    String contactId = null;
                    if (cursor != null && cursor.moveToFirst()) {
                        contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }

                    String givenName = null;///first name.
                    String familyName = null;//last name.

                    String projection[] = new String[]{ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME};
                    String whereName = ContactsContract.Data.MIMETYPE + " = ? AND " +
                            ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
                    String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, contactId};

                    nameCursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                            projection, whereName, whereNameParams, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

                    if(nameCursor != null && nameCursor.moveToNext()) {
                        givenName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                        familyName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), givenName, Toast.LENGTH_SHORT);
                    toast.show();

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }

                    if(nameCursor != null){
                        nameCursor.close();
                    }
                }
            }
        }
    }

    public void addGroupsToSpinner() {

        Spinner group_spinner = (Spinner) findViewById(R.id.au_choose_group_spinner);
        String[] groups = getResources().getStringArray(R.array.sample_groups);

        List<String> list = new ArrayList<String>();
        list.add("NO GROUP FILTER");

        for(int i = 0; i < groups.length; i++)
        {
            list.add(groups[i]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        group_spinner.setAdapter(dataAdapter);

    }

    public void populateUsers(){

        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Integer> imageArrayList = new ArrayList<Integer>();

        arrayList.add("jat");
        arrayList.add("dan");
        arrayList.add("an");
        arrayList.add("mwa");
        arrayList.add("mw");
        arrayList.add("wc");

        imageArrayList.add(R.drawable.user);
        imageArrayList.add(R.drawable.user);
        imageArrayList.add(R.drawable.user);
        imageArrayList.add(R.drawable.user);
        imageArrayList.add(R.drawable.user);
        imageArrayList.add(R.drawable.user);


        CustomAdapter userListAdapter = new CustomAdapter(this, arrayList, imageArrayList);

        ListView listView = (ListView) findViewById(R.id.existing_users_list_view);
        // Get a reference to the ListView, and attach this adapter to it.
        //listView.setAdapter(mTeamAdapter);
        listView.setAdapter(userListAdapter);

    }
}

