package com.example.android.politicalpreparedness.representative

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.util.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_voter_info.view.*
import java.util.Locale

class DetailFragment : Fragment() {


    private lateinit var fragmentRepresentativeBinding: FragmentRepresentativeBinding
    lateinit var address1:EditText
    lateinit var address2:EditText
    lateinit var city:EditText
    lateinit var zip:EditText
    lateinit var state:Spinner
    private val viewModel by viewModels<RepresentativeViewModel> {getViewModelFactory()}
    companion object {
        //TODO: Add Constant for Location request
    }

    //TODO: Declare ViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentRepresentativeBinding = FragmentRepresentativeBinding.inflate(inflater)
        fragmentRepresentativeBinding.lifecycleOwner = this
        //TODO: Establish bindings
        address1 = fragmentRepresentativeBinding.scrollableHeader.addressLine1
        address2 = fragmentRepresentativeBinding.scrollableHeader.addressLine2
        city = fragmentRepresentativeBinding.scrollableHeader.city
        state = fragmentRepresentativeBinding.scrollableHeader.state
        zip = fragmentRepresentativeBinding.scrollableHeader.zip

        //TODO: Define and assign Representative adapter

        //TODO: Populate Representative adapter

        //TODO: Establish button listeners for field and location search
        fragmentRepresentativeBinding.scrollableHeader.buttonSearch.setOnClickListener {
            hideKeyboard()
            if(inputIsValid()){
                val address = Address(address1.text.toString(),
                        address2.text.toString(),
                        city.text.toString(),
                        "state.selectedItem.toString()",
                        zip.text.toString())
                getRepList(address.toFormattedString())
            }
        }
        return fragmentRepresentativeBinding.root

    }

    private fun getRepList(address: String) {
        viewModel.getRepresentatives(address).observe(viewLifecycleOwner, Observer {
            println("Response "+it)
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
        inputManager.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        //TODO: Handle location permission result to get location on permission granted
//    }
//
//    private fun checkLocationPermissions(): Boolean {
//        return if (isPermissionGranted()) {
//            true
//        } else {
//            //TODO: Request Location permissions
//            false
//        }
//    }
//
//    private fun isPermissionGranted() : Boolean {
//        //TODO: Check if permission is already granted and return (true = granted, false = denied/other)
//        return true
//    }
//
//    private fun getLocation() {
//        //TODO: Get location from LocationServices
//        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
//    }
//
//    private fun geoCodeLocation(location: Location): Address {
//        val geocoder = Geocoder(context, Locale.getDefault())
//        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                .map { address ->
//                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
//                }
//                .first()
//    }
//
//    private fun hideKeyboard() {
//        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
//    }

}