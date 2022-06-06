package com.meeweel.carlist.ui.fragmentcardetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.carlist.app.App
import com.meeweel.carlist.data.repository.Repository
import com.meeweel.carlist.domain.CarModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CarDetailsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    init {
        App.appInstance.component.inject(this)
    }

    private val liveDataToObserve: MutableLiveData<CarModel> = MutableLiveData()

    fun getData(): LiveData<CarModel> {
        return liveDataToObserve
    }

    fun getCar(carId: Int) = getDataFromRepository(carId)

    private fun getDataFromRepository(carId: Int) {
        repository.getCarById(carId)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                liveDataToObserve.postValue(it)
            },{})
    }
}