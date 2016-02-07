package com.example.hganeshmurthy.instagramclient;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CommentsFragment extends DialogFragment {

    static ArrayList<PhotoComment>  comments;
    @Bind(R.id.lvComments) ListView lvComments;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance(ArrayList<PhotoComment> PhotoComments) {
        CommentsFragment frag = new CommentsFragment();
        Bundle args = new Bundle();
        comments  =  new ArrayList<PhotoComment>();
        comments = PhotoComments;
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        CommentsAdapter adapter = new CommentsAdapter(getContext(),comments);

        // Attach the adapter to a ListView
        lvComments.setAdapter(adapter);
        // Show soft keyboard automatically and request focus to field
        lvComments.requestFocus();

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        int dialogWidth = 200;
        int dialogHeight = 100;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

    }

}
