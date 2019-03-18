package com.tjdam007.androidtask.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tjdam007.androidtask.R
import com.tjdam007.androidtask.api.ApiService
import com.tjdam007.androidtask.model.Job
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LandingVM(application: Application) : AndroidViewModel(application) {
    /*
    * Get all available filters*/
    fun getFilters(): MutableLiveData<ArrayList<String>> {
        val data = MutableLiveData<ArrayList<String>>()
        data.postValue(arrayListOf("Location", "Job role", "Salary"))
        return data
    }

    /*
    * Get all posted jobs*/
    fun getJobs(location: String = "", page: Int): MutableLiveData<ArrayList<Job>> {
        val mutableLiveData = MutableLiveData<ArrayList<Job>>()
        val disposable = ApiService.jobServiceAPI
            .getJobsByLocation(location, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableLiveData.value = it

            }, {
                mutableLiveData.value = arrayListOf()
                it.printStackTrace()
                Toast.makeText(this@LandingVM.getApplication(), R.string.network_error, Toast.LENGTH_SHORT).show()
            })
        return mutableLiveData
    }

    /**
     * Search Job API
     * */
    fun searchJob(query: String): MutableLiveData<ArrayList<Job>> {
        val mutableLiveData = MutableLiveData<ArrayList<Job>>()
        val disposable = ApiService.jobServiceAPI
            .searchJobs(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableLiveData.value = it

            }, {
                mutableLiveData.value = arrayListOf()
                it.printStackTrace()
                Toast.makeText(this@LandingVM.getApplication(), R.string.network_error, Toast.LENGTH_SHORT).show()
            })
        return mutableLiveData
    }


}