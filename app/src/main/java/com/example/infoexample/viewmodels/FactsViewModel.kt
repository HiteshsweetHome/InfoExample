package com.example.infoexample.viewmodels

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.infoexample.DataRepository
import com.example.infoexample.model.AboutModel

class FactsViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var earningtObservable: LiveData<AboutModel>

    fun getEarningObservable() : LiveData<AboutModel>  {
        return earningtObservable
    }

    fun getFacts(){
        earningtObservable = DataRepository.getInstance().getFacts()
    }
}