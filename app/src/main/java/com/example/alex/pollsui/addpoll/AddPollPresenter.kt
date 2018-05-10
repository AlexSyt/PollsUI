package com.example.alex.pollsui.addpoll

import com.example.alex.pollsui.data.source.PollsDataSource

class AddPollPresenter(private val pollsRepository: PollsDataSource, private val addPollView: AddPollContract.View)
    : AddPollContract.Presenter {

    init {
        addPollView.presenter = this
    }

    override fun start() {
        // do nothing
    }

    override fun addQuestion() {
        addPollView.showAddQuestionDialog()
    }

    override fun saveQuestion(title: String, answers: List<String>) {

    }

    override fun savePoll() {
        // FIXME
        addPollView.showPollsList()
    }
}
