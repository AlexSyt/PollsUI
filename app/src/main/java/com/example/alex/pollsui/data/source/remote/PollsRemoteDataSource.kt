package com.example.alex.pollsui.data.source.remote

import com.example.alex.pollsui.data.Poll
import com.example.alex.pollsui.data.source.PollsDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PollsRemoteDataSource : PollsDataSource {

    private val apiService = PollsApiService.create()

    override fun getPolls(authorId: String?, callback: PollsDataSource.LoadPollsCallback) {
        getPollsCall(authorId).enqueue(object : Callback<List<Poll>> {
            override fun onResponse(call: Call<List<Poll>>?, response: Response<List<Poll>>?) {
                val polls = response?.body()
                if (polls != null) {
                    callback.onPollsLoaded(polls)
                } else {
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<List<Poll>>?, t: Throwable?) {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun getPollsCall(authorId: String?): Call<List<Poll>> {
        if (authorId != null) {
            return apiService.getMyPollsList()
        }
        return apiService.getPollsList()
    }

    override fun getPoll(pollId: String, callback: PollsDataSource.GetPollCallback) {
        apiService.getPoll(pollId).enqueue(object : Callback<Poll> {
            override fun onResponse(call: Call<Poll>?, response: Response<Poll>?) {
                val poll = response?.body()
                if (poll != null) {
                    callback.onPollLoaded(poll)
                } else {
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<Poll>?, t: Throwable?) {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun createPoll(poll: Poll, callback: PollsDataSource.CreatePollCallback) {
        apiService.createPoll(poll).enqueue(object : Callback<Poll> {
            override fun onResponse(call: Call<Poll>?, response: Response<Poll>?) {
                callback.onResult(true)
            }

            override fun onFailure(call: Call<Poll>?, t: Throwable?) {
                callback.onResult(false)
            }
        })
    }

    override fun submitPoll(poll: Poll) {
        // FIXME
    }

    companion object {

        private var INSTANCE: PollsRemoteDataSource? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: PollsRemoteDataSource().apply { INSTANCE = this }
    }
}
