package com.example.alex.pollsui.completepoll

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.example.alex.pollsui.R
import com.example.alex.pollsui.data.Poll
import com.example.alex.pollsui.data.Question

class CompletePollFragment : Fragment(), CompletePollContract.View {

    override lateinit var presenter: CompletePollContract.Presenter

    override var isActive = false
        get() = isAdded

    private lateinit var adapter: QuestionsAdapter

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_complete_poll, container, false)

        val rv = root.findViewById<RecyclerView>(R.id.recycler_view)
        rv.layoutManager = LinearLayoutManager(context)

        adapter = QuestionsAdapter(View.OnClickListener {
            val pair = it.tag as Pair<String?, String?>
            presenter.selectAnswer(pair.first, pair.second)
        })
        rv.adapter = adapter

        activity?.findViewById<FloatingActionButton>(R.id.fab)?.apply {
            setOnClickListener { presenter.submit() }
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_done))
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        view?.apply {
            findViewById<View>(R.id.progress_bar).visibility = if (active) View.VISIBLE else View.GONE
        }
    }

    override fun showPoll(poll: Poll) {
        (activity as CompletePollActivity).supportActionBar?.title = poll.title
        adapter.questions = poll.questions
    }

    override fun showError() {
        showMessage(getString(R.string.complete_poll_error))
    }

    override fun showPollsList() {
        activity?.finish()
    }

    private fun showMessage(text: String) {
        view?.let {
            Snackbar.make(it, text, Snackbar.LENGTH_LONG).show()
        }
    }

    private class QuestionsAdapter(private val listener: View.OnClickListener)
        : RecyclerView.Adapter<QuestionsAdapter.QuestionItemVh>() {

        var questions: List<Question> = ArrayList()
            set(questions) {
                field = questions
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionItemVh {
            return QuestionItemVh(LayoutInflater.from(parent.context)
                    .inflate(R.layout.question_list_item, parent, false))
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: QuestionItemVh, position: Int) {
            val question = questions[position]
            holder.title.text = question.title
            holder.answersContainer.removeAllViews()
            for (i in question.answers.indices) {
                val n = i + 1
                val rb = RadioButton(holder.itemView.context)
                rb.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                rb.text = "$n. ${question.answers[i].title}"
                rb.setOnClickListener(listener)
                rb.tag = Pair(question.id, question.answers[i].id)
                holder.answersContainer.addView(rb)
            }
        }

        override fun getItemCount() = questions.size

        private class QuestionItemVh(itemView: View?) : RecyclerView.ViewHolder(itemView) {

            lateinit var title: TextView
            lateinit var answersContainer: LinearLayout

            init {
                itemView?.apply {
                    title = findViewById(R.id.title)
                    answersContainer = findViewById(R.id.answers_container)
                }
            }
        }
    }

    companion object {
        fun newInstance() = CompletePollFragment()
    }
}
