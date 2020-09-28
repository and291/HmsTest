package ru.cardsmobile.hmstest.presentation.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.cardsmobile.hmstest.R

class SecurityFragment : Fragment() {

    private lateinit var textView: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_security, container, false).apply {
        textView = findViewById(R.id.text_security)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProvider(this)[SecurityViewModel::class.java].apply {
            text.observe(viewLifecycleOwner, Observer {
                textView.text = it.toString()
            })
        }
    }

    companion object {

        fun newInstance(): SecurityFragment = SecurityFragment()
    }
}
