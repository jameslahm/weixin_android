package com.MobileCourse.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.MobileCourse.R;

public class EditDialogFragment extends DialogFragment {

    public static final String TAG = "EditDialogFragment";

    public static EditDialogFragment display(FragmentManager fragmentManager) {
        EditDialogFragment dialogFragment = new EditDialogFragment();
        dialogFragment.show(fragmentManager, TAG);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_edit_dialog, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}