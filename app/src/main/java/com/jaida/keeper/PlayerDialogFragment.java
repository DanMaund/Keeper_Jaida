package com.jaida.keeper;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


    public class PlayerDialogFragment extends DialogFragment {

        private EditText mEditText;

        public PlayerDialogFragment() {
            // Empty constructor required for DialogFragment
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_player_dialog, container);
            mEditText = (EditText) view.findViewById(R.id.txt_your_name);
            getDialog().setTitle("Add Player");

            return view;
        }
    }