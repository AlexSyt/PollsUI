package com.example.alex.pollsui.data.source

import com.example.alex.pollsui.data.Poll

/**
 * Main entry point for accessing polls data.
 */
interface PollsDataSource {

    interface LoadPollsCallback {

        fun onPollsLoaded(polls: List<Poll>)

        fun onDataNotAvailable()
    }

    interface GetPollCallback {

        fun onPollLoaded(poll: Poll)

        fun onDataNotAvailable()
    }

    interface CreatePollCallback {
        fun onResult(successful: Boolean)
    }

    fun getPolls(authorId: String? = null, callback: LoadPollsCallback)

    fun getPoll(pollId: String?, callback: GetPollCallback)

    fun getPollWithStats(pollId: String?, callback: GetPollCallback)

    fun createPoll(poll: Poll, callback: CreatePollCallback)

    fun submitPoll(poll: Poll)
}
