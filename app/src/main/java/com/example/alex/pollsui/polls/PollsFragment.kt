package com.example.alex.pollsui.polls

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alex.pollsui.R
import com.example.alex.pollsui.addpoll.AddPollActivity
import com.example.alex.pollsui.completepoll.CompletePollActivity
import com.example.alex.pollsui.data.Poll

class PollsFragment : Fragment(), PollsContract.View {

    override lateinit var presenter: PollsContract.Presenter

    override var isActive = false
        get() = isAdded

    private lateinit var adapter: PollsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_polls, container, false)

        with(root) {
            val rv = findViewById<RecyclerView>(R.id.recycler_view)
            rv.layoutManager = LinearLayoutManager(context)
            rv.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

            adapter = PollsAdapter(ArrayList(0), View.OnClickListener {
                val pos = rv.getChildLayoutPosition(it)
                if (pos != RecyclerView.NO_POSITION) {
                    presenter.openPoll(adapter.polls[pos])
                }
            })
            rv.adapter = adapter

            findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).apply {
                setColorSchemeColors(
                        ContextCompat.getColor(context, R.color.colorPrimary),
                        ContextCompat.getColor(context, R.color.colorAccent),
                        ContextCompat.getColor(context, R.color.colorPrimaryDark)
                )
                setOnRefreshListener { presenter.loadPolls() }
            }
        }

        activity?.findViewById<FloatingActionButton>(R.id.fab_add)?.apply {
            setOnClickListener { presenter.addNewPoll() }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)) {
            post { isRefreshing = active }
        }
    }

    override fun showPolls(polls: List<Poll>) {
        adapter.polls = polls
    }

    override fun showAddPoll() {
        startActivity(Intent(context, AddPollActivity::class.java))
    }

    override fun showPollStatistic(poll: Poll) {
        AlertDialog.Builder(context!!)
                .setTitle(poll.title)
                .setMessage(poll.getStatisticString())
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show()
    }

    override fun startPoll(poll: Poll) {
        val intent = Intent(context, CompletePollActivity::class.java)
        intent.putExtra("poll_id", poll.id)
        startActivity(intent)
    }

    override fun showLoadingPollsError() {
        showMessage(getString(R.string.loading_error))
    }

    private fun showMessage(text: String) {
        view?.let {
            Snackbar.make(it, text, Snackbar.LENGTH_LONG).show()
        }
    }

    private class PollsAdapter(polls: List<Poll>, private val listener: View.OnClickListener)
        : RecyclerView.Adapter<PollsAdapter.PollItemVh>() {

        var polls: List<Poll> = polls
            set(polls) {
                field = polls
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollItemVh {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.poll_list_item, parent, false)
            view.setOnClickListener(listener)
            return PollItemVh(view)
        }

        override fun onBindViewHolder(holder: PollItemVh, position: Int) {
            val poll = polls[position]
            holder.title.text = poll.title
            holder.description.apply {
                text = context.getString(R.string.author, poll.author)
            }
        }

        override fun getItemCount() = polls.size

        private class PollItemVh(itemView: View?) : RecyclerView.ViewHolder(itemView) {

            lateinit var title: TextView
            lateinit var description: TextView

            init {
                itemView?.apply {
                    title = findViewById(R.id.title)
                    description = findViewById(R.id.description)
                }
            }
        }
    }

    companion object {
        fun newInstance() = PollsFragment()
    }
}
