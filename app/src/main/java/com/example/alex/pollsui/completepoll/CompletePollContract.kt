package com.example.alex.pollsui.completepoll

import com.example.alex.pollsui.BasePresenter
import com.example.alex.pollsui.BaseView
import com.example.alex.pollsui.data.Poll

interface CompletePollContract {

    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showPoll(poll: Poll)

        fun showError()

        fun showPollsList()
    }

    interface Presenter : BasePresenter {

        fun selectAnswer(questionId: String?, answerId: String?)

        fun submit()
    }
}
