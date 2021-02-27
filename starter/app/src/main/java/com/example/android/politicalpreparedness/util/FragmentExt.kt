package com.example.android.politicalpreparedness.util

import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Fragment.getViewModelFactory():ViewModelFactory{
    val repository = ElectionRepository()
    return ViewModelFactory(repository, this)
}

fun Fragment.requestPermissionsCompat(permissionsArray:Array<String>, requestCode:Int){
    activity?.let{
        ActivityCompat.requestPermissions(it,permissionsArray,requestCode)
    }
}


fun Fragment.shouldShowRequestPermissionRationaleCompat(permission:String) =
        activity?.shouldShowRequestPermissionRationale(permission)