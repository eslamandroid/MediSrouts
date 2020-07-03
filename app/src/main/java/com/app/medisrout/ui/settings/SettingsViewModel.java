package com.app.medisrout.ui.settings;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.medisrout.model.SharedData;

public class SettingsViewModel extends AndroidViewModel {

    private MutableLiveData<String> currentUserName;
    private MutableLiveData<String> currentNumberPatient;
    private MutableLiveData<String> maxNumberPatient;
    private MutableLiveData<String> toastMsg;

    private SharedData data;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        currentUserName = new MutableLiveData<>();
        currentNumberPatient = new MutableLiveData<>();
        maxNumberPatient = new MutableLiveData<>();
        data = new SharedData(application);
    }

    public MutableLiveData<String> getCurrentUserName() {
        return currentUserName;
    }

    public MutableLiveData<String> getCurrentNumberPatient() {
        return currentNumberPatient;
    }

    public MutableLiveData<String> getMaxNumberPatient() {
        return maxNumberPatient;
    }

    public void loadData() {
        String currentUser = data.getValue(SharedData.ReturnValue.STRING, "UserName");
        int numberPatient = data.getValue(SharedData.ReturnValue.INT, "MaxNumber");
        maxNumberPatient.setValue(String.valueOf(numberPatient > 0 ? numberPatient : 5));
        currentUserName.setValue(currentUser);
        currentNumberPatient.setValue(String.valueOf(0));
    }

    public void saveSettings() {
        String userName = currentUserName.getValue();
        String maxNumber = maxNumberPatient.getValue();

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(maxNumber)) {
            data.putValue("UserName", userName);
            data.putValue("MaxNumber", Integer.valueOf(maxNumber));
            toastMsg.setValue("SuccessFull");
        }
    }

    public MutableLiveData<String> getToastMsg() {
        return toastMsg;
    }
}