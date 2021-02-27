package com.example.android.politicalpreparedness.util

import androidx.fragment.app.Fragment

fun Fragment.getViewModelFactory():ViewModelFactory{
    val repository = ElectionRepository()
    return ViewModelFactory(repository, this)
}