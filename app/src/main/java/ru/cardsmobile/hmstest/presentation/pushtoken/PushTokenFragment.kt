package ru.cardsmobile.hmstest.presentation.pushtoken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.cardsmobile.hmstest.R

class PushTokenFragment : Fragment() {

    private lateinit var tvPushToken: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_push_token, container, false).apply {
        tvPushToken = findViewById(R.id.tv_push_token)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProvider(this)[PushTokenViewModel::class.java].apply {
            text.observe(viewLifecycleOwner, Observer {
                tvPushToken.text = it.toString()
            })
        }
    }

    companion object {

        fun newInstance(): PushTokenFragment = PushTokenFragment()
    }
}
