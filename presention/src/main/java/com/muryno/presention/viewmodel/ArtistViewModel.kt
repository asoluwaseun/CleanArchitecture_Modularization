package com.muryno.presention.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muryno.domain.model.ArtistDomainModel
import com.muryno.domain.usecase.ArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val artistUseCase: ArtistUseCase
) : ViewModel() {

    private val _artistState= MutableLiveData<List<ArtistDomainModel>>()
    var artistState: LiveData<List<ArtistDomainModel>> = _artistState



    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _loading


    private val _errorState: MutableLiveData<Boolean> = MutableLiveData()
    val errorState: LiveData<Boolean> = _errorState


    fun artistQuery(artistId: String) {
        viewModelScope.launch {
            try {
                artistUseCase.execute(artistId).let {
                    if (it.isNotEmpty()) {
                        _artistState.value = it
                    }else {
                        _artistState.value = emptyList()
                    }
                    _loading.postValue(false)
                }
            } catch (e: Exception) {
                _errorState.postValue(true)
                _loading.postValue(false)
            }
        }

    }

}





