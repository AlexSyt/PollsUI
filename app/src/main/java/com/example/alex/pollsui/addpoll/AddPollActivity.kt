package com.example.alex.pollsui.addpoll

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.alex.pollsui.Injection
import com.example.alex.pollsui.R
import com.example.alex.pollsui.util.replaceFragment
import com.example.alex.pollsui.util.setupActionBar

class AddPollActivity : AppCompatActivity() {

    private lateinit var addPollPresenter: AddPollContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_complete_poll)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.create_poll)
        }

        val addPollFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
                as AddPollFragment? ?: AddPollFragment.newInstance().also {
            replaceFragment(it, R.id.content_frame)
        }

        addPollPresenter = AddPollPresenter(Injection.providePollsRepository(), addPollFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_poll_activity_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.apply {
            if (itemId == R.id.action_save) {
                addPollPresenter.savePoll()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
