package com.app.medisrout.ui.home;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.medisrout.R;
import com.app.medisrout.model.PatientModel;
import com.app.medisrout.model.SharedData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> fullNameGroup;
    private MutableLiveData<String> ageGroup;
    private MutableLiveData<String> emailGroup;
    private MutableLiveData<String> toastMsg;
    private MutableLiveData<Integer> radioChecked;
    private MutableLiveData<List<PatientModel>> patientLiveData;


    private static final String TAG = "HomeViewModel";
    private SharedData sharedData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "HomeViewModel: ");
        toastMsg = new MutableLiveData<>();
        fullNameGroup = new MutableLiveData<>();
        ageGroup = new MutableLiveData<>();
        radioChecked = new MutableLiveData<>();
        emailGroup = new MutableLiveData<>();
        patientLiveData = new MutableLiveData<>();
        sharedData = new SharedData(application);
    }


    public MutableLiveData<String> getAgeGroup() {
        return ageGroup;
    }

    public MutableLiveData<List<PatientModel>> getPatientLiveData() {
        return patientLiveData;
    }

    public MutableLiveData<String> getToastMsg() {
        return toastMsg;
    }

    public MutableLiveData<String> getEmailGroup() {
        return emailGroup;
    }

    public MutableLiveData<String> getFullNameGroup() {
        return fullNameGroup;
    }

    public MutableLiveData<Integer> getRadioChecked() {
        return radioChecked;
    }

    private char getGender(int id) {
        switch (id) {
            case R.id.male:
                return 'm';
            case R.id.female:
                return 'f';
            default:
                return 'd';
        }
    }

    public void addPatient() {
        String fullName = fullNameGroup.getValue();
        String age = ageGroup.getValue();
        String email = emailGroup.getValue();
        int id = getRadioChecked().getValue();
        int maxPatient = sharedData.getValue(SharedData.ReturnValue.INT, "MaxNumber");
        String jsonPatient = sharedData.getValue(SharedData.ReturnValue.STRING, "listPatient");
        Type type = new TypeToken<List<PatientModel>>() {
        }.getType();
        List<PatientModel> list = new Gson().fromJson(jsonPatient, type);
        if (list == null) list = new ArrayList<>();
        if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(email)) {
            if (maxPatient > list.size()) {
                Log.d(TAG, "addPatient: " + maxPatient);
                list.add(new PatientModel(fullName, email, Integer.parseInt(age), getGender(id)));
                sharedData.putValue("listPatient", new Gson().toJson(list));
                patientLiveData.setValue(list);
            } else {
                toastMsg.setValue("Max Patient " + maxPatient);
            }
        }
    }

}