package com.example.alex.pollsui.completepoll

import android.text.TextUtils
import com.example.alex.pollsui.data.Poll
import com.example.alex.pollsui.data.source.PollsDataSource

class CompletePollPresenter(
        private val pollsRepository: PollsDataSource,
        private val completePollView: CompletePollContract.View,
        private val pollId: String?
) : CompletePollContract.Presenter {

    private lateinit var poll: Poll

    init {
        completePollView.presenter = this
    }

    override fun start() {
        completePollView.setLoadingIndicator(true)

        pollsRepository.getPoll(pollId, object : PollsDataSource.GetPollCallback {

            override fun onPollLoaded(poll: Poll) {
                if (!completePollView.isActive) {
                    return
                }
                this@CompletePollPresenter.poll = poll
                completePollView.setLoadingIndicator(false)
                completePollView.showPoll(poll)
            }

            override fun onDataNotAvailable() {
                if (!completePollView.isActive) {
                    return
                }
                completePollView.showError()
            }
        })
    }

    override fun selectAnswer(questionId: String?, answerId: String?) {
        for (question in poll.questions) {
            if (question.id != null && question.id.equals(questionId)) {
                question.selectedAnswer = answerId
            }
        }
    }

    override fun submit() {
        if (isAllQuestionsAnswered()) {
            pollsRepository.submitPoll(poll)
            completePollView.showPollsList()
        } else {
            completePollView.showError()
        }
    }

    private fun isAllQuestionsAnswered(): Boolean {
        for (question in poll.questions) {
            if (TextUtils.isEmpty(question.selectedAnswer)) {
                return false
            }
        }
        return true
    }
}
