package com.example.alex.pollsui.data.source.remote

import android.os.Handler
import com.example.alex.pollsui.data.Poll
import com.example.alex.pollsui.data.source.PollsDataSource

class PollsRemoteDataSource : PollsDataSource {

    private val DELAY = 2500L

    override fun getPolls(authorId: String?, callback: PollsDataSource.LoadPollsCallback) {
        // FIXME
        Handler().postDelayed({
            callback.onDataNotAvailable()
        }, DELAY)
    }

    override fun getPoll(pollId: String, callback: PollsDataSource.GetPollCallback) {
        // FIXME
        Handler().postDelayed({
            callback.onDataNotAvailable()
        }, DELAY)
    }

    override fun createPoll(poll: Poll) {
        TODO("not implemented")
    }

    override fun submitPoll(poll: Poll) {
        TODO("not implemented")
    }
}
