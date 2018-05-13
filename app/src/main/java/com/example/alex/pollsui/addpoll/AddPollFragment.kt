package com.example.alex.pollsui.addpoll

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.alex.pollsui.R
import com.example.alex.pollsui.data.Question

class AddPollFragment : Fragment(), AddPollContract.View {

    override lateinit var presenter: AddPollContract.Presenter

    override var isActive = false
        get() = isAdded

    private lateinit var adapter: QuestionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_add_poll, container, false)

        adapter = QuestionsAdapter()
        val rv = root.findViewById<RecyclerView>(R.id.recycler_view)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter

        activity?.findViewById<FloatingActionButton>(R.id.fab_add)?.apply {
            setOnClickListener { presenter.addQuestion() }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun getTitle(): String {
        view?.apply {
            return findViewById<EditText>(R.id.title).text.toString().trim()
        }
        return ""
    }

    @SuppressLint("InflateParams")
    override fun showAddQuestionDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_question, null)
        AlertDialog.Builder(context)
                .setTitle(R.string.add_question)
                .setView(view)
                .setPositiveButton(android.R.string.ok, { _, _ ->
                    val title = view.findViewById<EditText>(R.id.title).text.toString()
                    val firstAnswer = view.findViewById<EditText>(R.id.first_answer).text.toString()
                    val secondAnswer = view.findViewById<EditText>(R.id.second_answer).text.toString()
                    val thirdAnswer = view.findViewById<EditText>(R.id.third_answer).text.toString()
                    presenter.saveQuestion(title, listOf(firstAnswer, secondAnswer, thirdAnswer))
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show()
    }

    override fun showQuestion(question: Question) {
        adapter.questions.add(question)
        adapter.notifyDataSetChanged()
    }

    override fun showPollsList() {
        activity?.finish()
    }

    override fun showInvalidQuestionError() {
        showMessage(getString(R.string.invalid_question_error))
    }

    override fun showInvalidPollError() {
        showMessage(getString(R.string.invalid_poll_error))
    }

    private fun showMessage(text: String) {
        view?.let {
            Snackbar.make(it, text, Snackbar.LENGTH_LONG).show()
        }
    }

    private class QuestionsAdapter : RecyclerView.Adapter<QuestionsAdapter.QuestionItemVh>() {

        var questions: MutableList<Question> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionItemVh {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.question_list_item, parent, false)
            return QuestionItemVh(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: QuestionItemVh, position: Int) {
            val question = questions[position]
            holder.title.text = question.title
            holder.answersContainer.removeAllViews()
            for (i in question.answers.indices) {
                val n = i + 1
                val textView = TextView(holder.itemView.context)
                textView.text = "$n. ${question.answers[i].title}"
                holder.answersContainer.addView(textView)
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
        fun newInstance() = AddPollFragment()
    }
}
