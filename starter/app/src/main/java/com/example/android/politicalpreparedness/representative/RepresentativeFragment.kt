package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.util.getViewModelFactory
import com.example.android.politicalpreparedness.util.requestPermissionsCompat
import com.example.android.politicalpreparedness.util.shouldShowRequestPermissionRationaleCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale

class DetailFragment : Fragment() {


    private lateinit var fragmentRepresentativeBinding: FragmentRepresentativeBinding
    lateinit var address1:EditText
    lateinit var address2:EditText
    lateinit var city:EditText
    lateinit var zip:EditText
    lateinit var state:Spinner
    private val viewModel by viewModels<RepresentativeViewModel> {getViewModelFactory()}
    lateinit var representativeAdapter:RepresentativeListAdapter
    companion object {
        val PERMISSION_REQUEST_LOCATION = 112
        var firstTime = true
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentRepresentativeBinding = FragmentRepresentativeBinding.inflate(inflater)
        fragmentRepresentativeBinding.lifecycleOwner = this
        address1 = fragmentRepresentativeBinding.scrollableHeader.addressLine1
        address2 = fragmentRepresentativeBinding.scrollableHeader.addressLine2
        city = fragmentRepresentativeBinding.scrollableHeader.city
        state = fragmentRepresentativeBinding.scrollableHeader.state
        zip = fragmentRepresentativeBinding.scrollableHeader.zip
        val states = resources.getStringArray(R.array.states)
        val adapter = activity?.applicationContext?.let{
            ArrayAdapter(it,android.R.layout.simple_spinner_item,states)
        }
        state.adapter = adapter
        fragmentRepresentativeBinding.scrollableHeader.buttonSearch.setOnClickListener {
            hideKeyboard()
            if(inputIsValid()){
                val address = Address(address1.text.toString(),
                        address2.text.toString(),
                        city.text.toString(),
                        state.selectedItem.toString(),
                        zip.text.toString())
                getRepList(address.toFormattedString())
            }
        }
        fragmentRepresentativeBinding.scrollableHeader.buttonLocation.setOnClickListener {
            if(checkLocationPermission()){
                getLocation()
            }
        }
        return fragmentRepresentativeBinding.root

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        try {
            val locationRequest = LocationRequest()
            locationRequest.setInterval(10000)
            locationRequest.setFastestInterval(3000)
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

            val locationData = activity?.applicationContext?.let { LocationServices.getFusedLocationProviderClient(it) }

            locationData?.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult?) {
                    super.onLocationResult(result)
                    if (result != null && result.locations.size > 0) {
                        val latestLocationIndex = result.locations.size - 1
                        val address = getCodeLocation(result.locations[latestLocationIndex])
                        fragmentRepresentativeBinding.scrollableHeader.address = address
                        if (firstTime) {
                            getRepList(address.toFormattedString())
                        }

                    }
                }
            }, Looper.getMainLooper())


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getCodeLocation(location: Location): Address {
        val geocoder = Geocoder(activity?.applicationContext, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude,1).map{ address ->
            Address(address.thoroughfare,address.subThoroughfare, address.locality, address.adminArea, address.postalCode)

        }.first()
    }

    private fun checkLocationPermission(): Boolean {
        return if(isPermissionGranted()){
            true
        }else{
            requestLocationPermission()
            false
        }
    }

    private fun requestLocationPermission() {
        if(this.shouldShowRequestPermissionRationaleCompat(Manifest.permission.ACCESS_FINE_LOCATION)!!){
            requestPermissionsCompat(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_LOCATION)
            showToast("Location Permission is required")
        }else{
            requestPermissionsCompat(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_LOCATION)
            showToast("Location Permission is not available")
        }
    }

    private fun isPermissionGranted(): Boolean {
        return checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkSelfPermission(permission: String) =
        ActivityCompat.checkSelfPermission(requireActivity().applicationContext,permission)


    private fun getRepList(address: String) {
        firstTime = false
        viewModel.getRepresentatives(address).observe(viewLifecycleOwner, Observer {
            representativeAdapter = RepresentativeListAdapter()
            fragmentRepresentativeBinding.viewModel = viewModel
            fragmentRepresentativeBinding.representativeRecyclerView.adapter = representativeAdapter
        })
    }

    private fun inputIsValid(): Boolean {
        var error = true
        if(address1.text.isEmpty()){
            error = false
            showToast("Address is Empty")
        }
        if(zip.text.isEmpty()){
            error = false
            showToast("Zip Code is empty")
        }
        if(city.text.isEmpty()){
            error== false
            showToast("City is empty")
        }
        return error
    }

    private fun showToast(message: String) {
        println("Error ")
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show()
    }

    private fun hideKeyboard() {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)  as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}