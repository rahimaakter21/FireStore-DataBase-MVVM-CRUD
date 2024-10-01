package com.example.firebaserealtimedatabasemvvmcrud.viewmodel

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaserealtimedatabasemvvmcrud.model.Data
import com.example.firebaserealtimedatabasemvvmcrud.repository.DataRepository

class DataViewModel: ViewModel() {

    private  val dataRepository = DataRepository()
    private  var _dataList : MutableLiveData<List<Data>> = dataRepository.fetchData()
    val dataList:LiveData<List<Data>>  = _dataList

    private  val _error = MutableLiveData<String?>()
    val error:MutableLiveData<String?>get() = _error

    fun addData(data: Data,onSuccess:()->Unit,onFailure:()->Unit){
        dataRepository.addData(data)
            .addOnSuccessListener { onSuccess()}
        .addOnFailureListener {
            _error.value = it.message
            onFailure()
        }

    }
    fun updateData(data: Data){

        dataRepository.updateData(data)

    }
    fun deleteData(data: Data,onSuccess:()->Unit,onFailure:()->Unit){
        dataRepository.deleteData(data)

            onSuccess()
    }
    fun handleDatabaseError(errorMessage: String){
        _error.value = errorMessage

    }

}