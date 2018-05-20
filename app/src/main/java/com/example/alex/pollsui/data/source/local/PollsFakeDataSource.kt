package com.example.alex.pollsui.data.source.local

import android.util.Log
import com.example.alex.pollsui.data.Answer
import com.example.alex.pollsui.data.Poll
import com.example.alex.pollsui.data.Question
import com.example.alex.pollsui.data.source.PollsDataSource

class PollsFakeDataSource : PollsDataSource {

    private val suffixes = mapOf(1 to "First", 2 to "Second", 3 to "Third", 4 to "Fourth")

    private val polls by lazy {
        Log.d("FakeData", "Init polls")
        val res = LinkedHashMap<String?, Poll>()
        suffixes.keys.forEach {
            val poll = generatePoll(it)
            res[poll.id] = poll
        }
        res
    }

    private val questions by lazy {
        Log.d("FakeData", "Init questions")
        val res = ArrayList<Question>()
        suffixes.keys.forEach {
            val question = Question("Question".addSuffix(it))
            question.answers.addAll(answers)
            res.add(question)
        }
        res
    }

    private val answers by lazy {
        Log.d("FakeData", "Init answers")
        val res = ArrayList<Answer>()
        suffixes.keys.forEach {
            res.add(Answer("Answer".addSuffix(it)))
        }
        res
    }

    override fun getPolls(authorId: String?, callback: PollsDataSource.LoadPollsCallback) {
        callback.onPollsLoaded(ArrayList(polls.values))
    }

    override fun getPoll(pollId: String, callback: PollsDataSource.GetPollCallback) {
        val poll = polls[pollId]
        if (poll != null) {
            callback.onPollLoaded(poll)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun createPoll(poll: Poll) {
        polls[poll.id] = poll
    }

    override fun submitPoll(poll: Poll) {
        // do nothing
    }


    private fun generatePoll(index: Int): Poll {
        val author = "Author".addSuffix(index)
        val poll = Poll("Poll".addSuffix(index), author, "id".addSuffix(index))
        poll.questions.addAll(questions)
        return poll
    }


    private fun String.addSuffix(index: Int): String {
        val suffix = suffixes[index]
        return "$suffix $this"
    }

    companion object {

        private var INSTANCE: PollsFakeDataSource? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: PollsFakeDataSource().apply { INSTANCE = this }
    }
}
