package com.practice.dagger_hilt

import androidx.lifecycle.ViewModel
import com.practice.dagger_hilt.domain.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// inject all the dependencies in the constructor
// take a look at in the module(AppModule.kt) and see if hilt can find this
//
@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository
): ViewModel() {
}