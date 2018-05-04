package com.example.alex.pollsui.polls

import com.example.alex.pollsui.BasePresenter
import com.example.alex.pollsui.BaseView
import com.example.alex.pollsui.data.Poll

interface PollsContract {

    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showPolls(polls: List<Poll>)

        fun showAddPoll()

        fun showPollStatistic(poll: Poll)

        fun startPoll(poll: Poll)

        fun showLoadingPollsError()
    }

    interface Presenter : BasePresenter {

        var currentFiltering: PollsFilteringType

        fun loadPolls()

        fun addNewPoll()

        fun openPoll(poll: Poll)
    }
}
