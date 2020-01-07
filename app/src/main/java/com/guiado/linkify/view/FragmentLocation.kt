package com.guiado.linkify.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentLocationBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.LocationViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*


class FragmentLocation : BaseFragment(), GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var mylocation: Location? = null
    private var googleApiClient: GoogleApiClient? = null
    private val REQUEST_CHECK_SETTINGS_GPS = 0x1
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2

    @Transient
    lateinit internal var areaViewModel: LocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding =
                DataBindingUtil.inflate<FragmentLocationBinding>(inflater, R.layout.fragment_location, container, false)
        areaViewModel = LocationViewModel(this.context!!, this)
        binding.homeData = areaViewModel
        doShowInfoDialog()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        areaViewModel.colorResource.observe(this, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t!!) {
                    checkPermissions()
                    setUpGClient()
                }
            }
        })
    }

    @Synchronized
    private fun setUpGClient() {
        googleApiClient = GoogleApiClient.Builder(context!!)
                .enableAutoManage(activity!!, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient!!.connect()
    }


    override fun onLocationChanged(location: Location) {
        mylocation = location
        if (mylocation != null) {
            val latitude = mylocation!!.latitude
            val longitude = mylocation!!.longitude
        }
    }


    private fun getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient!!.isConnected()) {
                val permissionLocation = ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
                    val locationRequest = LocationRequest()
                    locationRequest.interval = 3000
                    locationRequest.fastestInterval = 3000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    val builder = LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                    builder.setAlwaysShow(true)
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
                    val result = LocationServices.SettingsApi
                            .checkLocationSettings(googleApiClient, builder.build())
                    result.setResultCallback(object : ResultCallback<LocationSettingsResult> {

                        override fun onResult(result: LocationSettingsResult) {
                            val status = result.status
                            when (status.statusCode) {
                                LocationSettingsStatusCodes.SUCCESS -> {
                                    if (ContextCompat.checkSelfPermission(
                                                    context!!, Manifest.permission.ACCESS_FINE_LOCATION)
                                            == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient)
                                    }
                                }
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                                    try {
                                      status.startResolutionForResult(activity!!,
                                                REQUEST_CHECK_SETTINGS_GPS)
                                    } catch (e: IntentSender.SendIntentException) {
                                    }

                                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                }
                            }
                        }
                    })
                }
            }
        }
    }

    private fun checkPermissions() {
        val permissionLocation = ContextCompat.checkSelfPermission(
                context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        val listPermissionsNeeded = ArrayList<String>()
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (!listPermissionsNeeded.isEmpty()) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_ID_MULTIPLE_PERMISSIONS)
            }
        } else {
            getMyLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val permissionLocation = ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation()
        }
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }

    fun showInfoDialog(countriesInfoModel: LocationViewModel) {
        val alert = LocationOptionDialog()
        alert.showDialog(this.activity, countriesInfoModel)
    }

    override fun onConnected(p0: Bundle?) {
        //checkPermissions()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CHECK_SETTINGS_GPS -> {
                when (resultCode) {
                    Activity.RESULT_OK -> { getMyLocation(); }
                }
            }
        }
    }

    private fun doShowInfoDialog() {
        val permissionLocation = ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation()
        } else{
            showInfoDialog(areaViewModel)
        }
    }

}
