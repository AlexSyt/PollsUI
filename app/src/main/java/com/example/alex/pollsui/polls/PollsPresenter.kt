package com.example.alex.pollsui.polls

import com.example.alex.pollsui.data.Poll
import com.example.alex.pollsui.data.source.PollsDataSource

class PollsPresenter(private val pollsRepository: PollsDataSource, private val pollsView: PollsContract.View)
    : PollsContract.Presenter {

    override var currentFiltering = PollsFilteringType.MY_POLLS

    init {
        pollsView.presenter = this
    }

    override fun start() {
        loadPolls()
    }

    override fun loadPolls() {
        pollsView.setLoadingIndicator(true)

        val author = if (currentFiltering == PollsFilteringType.MY_POLLS) "author" else null

        pollsRepository.getPolls(author, object : PollsDataSource.LoadPollsCallback {

            override fun onPollsLoaded(polls: List<Poll>) {
                if (!pollsView.isActive) {
                    return
                }
                pollsView.setLoadingIndicator(false)
                pollsView.showPolls(polls)
            }

            override fun onDataNotAvailable() {
                if (!pollsView.isActive) {
                    return
                }
                pollsView.showLoadingPollsError()
            }
        })
    }

    override fun addNewPoll() {
        pollsView.showAddPoll()
    }

    override fun openPoll(poll: Poll) {
        when (currentFiltering) {
            PollsFilteringType.MY_POLLS -> loadStats(poll)
            PollsFilteringType.OTHER_POLLS -> pollsView.startPoll(poll)
        }
    }

    private fun loadStats(poll: Poll) {
        pollsView.setLoadingIndicator(true)
        pollsRepository.getPollWithStats(poll.id, object : PollsDataSource.GetPollCallback {

            override fun onPollLoaded(poll: Poll) {
                if (!pollsView.isActive) {
                    return
                }
                pollsView.setLoadingIndicator(false)
                pollsView.showPollStatistic(poll)
            }

            override fun onDataNotAvailable() {
                if (!pollsView.isActive) {
                    return
                }
                pollsView.setLoadingIndicator(false)
            }
        })
    }
}
