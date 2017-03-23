package ru.supervital.test404group.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.supervital.test404group.R;


/**
 * Created by Vitaly Oantsa on 23.03.2017.
 */

public class StartFragment extends Fragment {
    private static final String TAG = StartFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

}
