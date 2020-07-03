package com.app.medisrout.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.medisrout.R;

public class SettingsFragment extends Fragment {


    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        com.app.medisrout.databinding.FragmentSettingsBinding fragmentSettingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        fragmentSettingsBinding.setSettingsViewModel(settingsViewModel);
        return fragmentSettingsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settingsViewModel.loadData();
        settingsViewModel.getToastMsg().removeObservers(this);
        settingsViewModel.getToastMsg().observe(getViewLifecycleOwner(), s -> Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show());
    }
}