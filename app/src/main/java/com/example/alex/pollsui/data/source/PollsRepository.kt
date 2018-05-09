package com.example.alex.pollsui.data.source

import com.example.alex.pollsui.data.Poll

/**
 * Concrete implementation to load polls from the data sources. For simplicity, cache in not used.
 */
class PollsRepository(
        private val remoteDataSource: PollsDataSource,
        private val fakeDataSource: PollsDataSource
) : PollsDataSource {

    override fun getPolls(authorId: String?, callback: PollsDataSource.LoadPollsCallback) {
        remoteDataSource.getPolls(authorId, object : PollsDataSource.LoadPollsCallback {
            override fun onPollsLoaded(polls: List<Poll>) {
                callback.onPollsLoaded(polls)
            }

            override fun onDataNotAvailable() {
                fakeDataSource.getPolls(authorId, callback)
            }
        })
    }

    override fun getPoll(pollId: String, callback: PollsDataSource.GetPollCallback) {
        remoteDataSource.getPoll(pollId, object : PollsDataSource.GetPollCallback {
            override fun onPollLoaded(poll: Poll) {
                callback.onPollLoaded(poll)
            }

            override fun onDataNotAvailable() {
                fakeDataSource.getPoll(pollId, callback)
            }
        })
    }

    override fun createPoll(poll: Poll) {
        remoteDataSource.createPoll(poll)
    }

    override fun submitPoll(poll: Poll) {
        remoteDataSource.createPoll(poll)
    }

    companion object {

        private var INSTANCE: PollsRepository? = null

        @JvmStatic
        fun getInstance(remoteDataSource: PollsDataSource,
                        fakeDataSource: PollsDataSource): PollsRepository {
            return INSTANCE ?: PollsRepository(remoteDataSource, fakeDataSource)
                    .apply { INSTANCE = this }
        }
    }
}
