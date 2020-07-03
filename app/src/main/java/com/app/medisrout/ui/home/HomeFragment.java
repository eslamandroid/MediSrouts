package com.app.medisrout.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.app.medisrout.databinding.FragmentHomeBinding;
import com.app.medisrout.model.PatientModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FragmentHomeBinding fragmentHomeBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        fragmentHomeBinding.setHomeViewModel(homeViewModel);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StringBuilder stringBuilder = new StringBuilder();
        homeViewModel.getPatientLiveData().removeObservers(this);
        homeViewModel.getPatientLiveData().observe(getViewLifecycleOwner(), patientModels -> {
            for (PatientModel patientModel : patientModels) {
                stringBuilder.append(patientModel.toString()).append("\n");
                Log.d("HomeViewModel", "onActivityCreated: " + stringBuilder.toString());
            }
            fragmentHomeBinding.listDetails.setText(stringBuilder.toString());
        });

        homeViewModel.getToastMsg().removeObservers(this);
        homeViewModel.getToastMsg().observe(getViewLifecycleOwner(), s -> Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show());

    }
}