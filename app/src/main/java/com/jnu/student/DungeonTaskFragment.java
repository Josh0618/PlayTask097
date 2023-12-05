package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DungeonTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DungeonTaskFragment extends Fragment {

    private WebView webView;
    public DungeonTaskFragment() {
        // Required empty public constructor
    }

    public static DungeonTaskFragment newInstance() {
        DungeonTaskFragment fragment = new DungeonTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);
        return rootView;
    }
}