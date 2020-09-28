package ru.cardsmobile.hmstest.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.cardsmobile.hmstest.R

class NoServicesMapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_no_services, container, false)

    companion object {

        fun newInstance(): NoServicesMapFragment =
            NoServicesMapFragment()
    }
}
