package ru.cardsmobile.hmstest.presentation.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.cardsmobile.hmstest.R

class LocationFragment : Fragment() {

    private lateinit var btnLastLocation: Button
    private lateinit var btnUpdatedLocation: Button
    private lateinit var btnRemoveUpdatedLocation: Button
    private lateinit var btnCheckSettings: Button
    private lateinit var tvLocationInfo: TextView
    private lateinit var tvOperationResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_location, container, false).apply {
        btnLastLocation = findViewById(R.id.btn_get_last_location)
        btnUpdatedLocation = findViewById(R.id.btn_get_updated_location)
        btnRemoveUpdatedLocation = findViewById(R.id.btn_remove_updated_location)
        btnCheckSettings = findViewById(R.id.btn_check_settings_location)
        tvLocationInfo = findViewById(R.id.tv_location_info)
        tvOperationResult = findViewById(R.id.tv_operation_result)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProvider(this)[LocationViewModel::class.java].apply {
            operationText.observe(viewLifecycleOwner, Observer {
                tvOperationResult.text = it.toString()
            })
            additionalText.observe(viewLifecycleOwner, Observer {
                tvLocationInfo.text = "${it.first}, ${it.second}"
            })
            btnLastLocation.setOnClickListener {
                clearTv()
                getLastLocation()
            }
            btnUpdatedLocation.setOnClickListener {
                clearTv()
                getLocationUpdates()
            }
            btnRemoveUpdatedLocation.setOnClickListener {
                clearTv()
                removeLocationUpdates()
            }
            btnCheckSettings.setOnClickListener {
                clearTv()
                checkLocationSettings()
            }
        }
    }

    private fun clearTv() {
        tvLocationInfo.text = null
        tvOperationResult.text = null
    }

    companion object {

        fun newInstance() = LocationFragment()
    }
}