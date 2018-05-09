package com.example.alex.pollsui.addpoll

import com.example.alex.pollsui.BasePresenter
import com.example.alex.pollsui.BaseView
import com.example.alex.pollsui.data.Question

interface AddPollContract {

    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun getTitle(): String

        fun showAddQuestionDialog()

        fun showQuestion(question: Question)

        fun showPollsList()

        fun showInvalidDataError()
    }

    interface Presenter : BasePresenter {

        fun addQuestion()

        fun saveQuestion(title: String, answers: List<String>)

        fun savePoll()
    }
}
