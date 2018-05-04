package com.example.alex.pollsui.polls

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.alex.pollsui.R
import com.example.alex.pollsui.data.source.PollsRepository
import com.example.alex.pollsui.data.source.local.PollsFakeDataSource
import com.example.alex.pollsui.util.replaceFragment
import com.example.alex.pollsui.util.setupActionBar

class PollsActivity : AppCompatActivity() {

    private val CURRENT_FILTERING_KEY = "current_filtering"

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var pollsPresenter: PollsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polls)

        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.ic_action_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))

        val pollsFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
                as PollsFragment? ?: PollsFragment.newInstance().also {
            replaceFragment(it, R.id.content_frame)
        }

        val repository = PollsRepository(PollsFakeDataSource(), PollsFakeDataSource()) // FIXME(create remote data source)

        pollsPresenter = PollsPresenter(repository, pollsFragment).apply {
            savedInstanceState?.apply {
                currentFiltering = getSerializable(CURRENT_FILTERING_KEY) as PollsFilteringType
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateTitle()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putSerializable(CURRENT_FILTERING_KEY, pollsPresenter.currentFiltering)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.apply {
            if (itemId == android.R.id.home) {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateTitle() {
        findViewById<Toolbar>(R.id.toolbar).apply {
            title = when (pollsPresenter.currentFiltering) {
                PollsFilteringType.MY_POLLS -> getString(R.string.my_polls)
                PollsFilteringType.OTHER_POLLS -> getString(R.string.other_polls)
            }
        }
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_my_polls -> pollsPresenter.currentFiltering = PollsFilteringType.MY_POLLS
                R.id.action_other_polls -> pollsPresenter.currentFiltering = PollsFilteringType.OTHER_POLLS
            }
            updateTitle()
            pollsPresenter.loadPolls()
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
    }
}
