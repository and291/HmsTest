package ru.cardsmobile.hmstest.presentation.map.huawei

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import ru.cardsmobile.hmstest.R
import ru.cardsmobile.hmstest.di.Di
import ru.cardsmobile.hmstest.presentation.map.OnGeoMapReadyCallback

class HuaweiMapFragment : Fragment(),
    OnMapReadyCallback {

    private val geoMapFactory = Di.geoMapFactory

    private lateinit var mapView: MapView
    private var callback: OnGeoMapReadyCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as? OnGeoMapReadyCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_huawei_map, container, false).apply {
        mapView = findViewById(R.id.mv_map)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    //region OnMapReadyCallback
    override fun onMapReady(huaweiMap: HuaweiMap?) {
        if (huaweiMap != null) {
            val geoMap = geoMapFactory.create(huaweiMap)
            callback?.onMapReady(geoMap)
        }
    }
    //endregion

    companion object {

        fun newInstance(): HuaweiMapFragment =
            HuaweiMapFragment()
    }
}
