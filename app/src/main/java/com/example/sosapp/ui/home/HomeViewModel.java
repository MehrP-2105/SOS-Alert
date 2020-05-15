package com.example.sosapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hello! Say 'help' 3 times or press the button to trigger an SOS Alert.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}