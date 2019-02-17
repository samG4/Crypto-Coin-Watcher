package com.shadow.cryptowatcher

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shadow.cryptowatcher.fragments.EventFragment
import com.shadow.cryptowatcher.fragments.HomeFragment
import com.shadow.cryptowatcher.fragments.WatcherFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val KEY_CURRENT_SELECTION = "current_screen"
    private val DEFAULT_SCREEN = R.id.navigate_home
    private var currentNavigation = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?.let {
            currentNavigation = savedInstanceState.getInt(KEY_CURRENT_SELECTION, -1)
            if (currentNavigation == -1) {
                currentNavigation = R.id.navigate_home
            }
        } ?: loadView(DEFAULT_SCREEN)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun loadView(selection: Int) {
        if (currentNavigation == selection) {
            return
        }
        currentNavigation = selection
        val fragment: Fragment? = when (currentNavigation) {
            R.id.navigate_home -> HomeFragment()
            R.id.navigate_watcher -> WatcherFragment()
            R.id.navigate_forecast -> EventFragment()
            else -> null
        }
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        loadView(item.itemId)
        return@OnNavigationItemSelectedListener true
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_SELECTION, currentNavigation)
    }
}
