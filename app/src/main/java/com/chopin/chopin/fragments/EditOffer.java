package com.chopin.chopin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopin.chopin.R;

public class EditOffer extends Fragment {


    public EditOffer() {
        // Required empty public constructor
    }


    public static EditOffer newInstance() {
        EditOffer fragment = new EditOffer();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_offer, container, false);
    }
}
