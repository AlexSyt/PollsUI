package com.example.alex.pollsui.addpoll

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

    override fun showAddQuestionDialog() {
        showMessage("Add question dialog") // FIXME
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
