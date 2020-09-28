package ru.cardsmobile.hmstest.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.cardsmobile.hmstest.R
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Google
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Huawei
import ru.cardsmobile.hmstest.presentation.map.google.GoogleMapFragment
import ru.cardsmobile.hmstest.presentation.map.huawei.HuaweiMapFragment

class MapFragment : Fragment(),
    OnGeoMapReadyCallback {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProvider(this)[MapViewModel::class.java].apply {
            mobileServiceType.observe(viewLifecycleOwner, Observer { result ->
                val fragment = when (result.getOrNull()) {
                    Google -> GoogleMapFragment.newInstance()
                    Huawei -> HuaweiMapFragment.newInstance()
                    else -> NoServicesMapFragment.newInstance()
                }
                replaceFragment(fragment)
            })
        }
    }

    //region OnGeoMapReadyCallback
    override fun onMapReady(geoMap: GeoMap) {
        geoMap.uiSettings.isZoomControlsEnabled = true
    }
    //endregion

    private fun replaceFragment(fragment: Fragment): Boolean = childFragmentManager.run {
        val currentFragment = findFragmentById(R.id.content)
        if (currentFragment?.javaClass != fragment.javaClass) {
            beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()
            true
        } else {
            false
        }
    }

    companion object {

        fun newInstance(): MapFragment = MapFragment()
    }
}
