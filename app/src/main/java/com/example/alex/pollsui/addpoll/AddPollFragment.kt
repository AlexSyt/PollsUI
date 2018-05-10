package com.example.alex.pollsui.addpoll

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.alex.pollsui.R
import com.example.alex.pollsui.data.Question

class AddPollFragment : Fragment(), AddPollContract.View {

    override lateinit var presenter: AddPollContract.Presenter

    override var isActive = false
        get() = isAdded

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_add_poll, container, false)

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
        TODO("not implemented")
    }

    override fun showPollsList() {
        activity?.finish()
    }

    override fun showInvalidDataError() {
        showMessage(getString(R.string.invalid_data_error))
    }

    private fun showMessage(text: String) {
        view?.let {
            Snackbar.make(it, text, Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        fun newInstance() = AddPollFragment()
    }
}
