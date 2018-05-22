package com.example.alex.pollsui.completepoll

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.alex.pollsui.Injection
import com.example.alex.pollsui.R
import com.example.alex.pollsui.util.replaceFragment
import com.example.alex.pollsui.util.setupActionBar

class CompletePollActivity : AppCompatActivity() {

    private lateinit var completePollPresenter: CompletePollPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_complete_poll)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.complete_poll)
        }

        val completePollFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
                as CompletePollFragment? ?: CompletePollFragment.newInstance().also {
            replaceFragment(it, R.id.content_frame)
        }

        val pollId = intent.extras.getString("poll_id")
        completePollPresenter = CompletePollPresenter(Injection.providePollsRepository(), completePollFragment, pollId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
