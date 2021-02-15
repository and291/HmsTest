package ru.cardsmobile.hmstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.cardsmobile.hmstest.presentation.location.LocationFragment
import ru.cardsmobile.hmstest.presentation.map.MapFragment
import ru.cardsmobile.hmstest.presentation.pushtoken.PushTokenFragment
import ru.cardsmobile.hmstest.presentation.security.SecurityFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.navigation_push_token -> PushTokenFragment.newInstance()
                R.id.navigation_map -> MapFragment.newInstance()
                R.id.navigation_security -> SecurityFragment.newInstance()
                R.id.navigation_location -> LocationFragment.newInstance()
                else -> throw IllegalStateException()
            }
            replaceFragment(fragment)
        }

        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_push_token
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean = supportFragmentManager.run {
        val currentFragment = findFragmentById(R.id.content)
        if (currentFragment?.javaClass != fragment.javaClass) {
            beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
            true
        } else {
            false
        }
    }
}
