package com.example.alex.pollsui.addpoll

import com.example.alex.pollsui.data.Answer
import com.example.alex.pollsui.data.Poll
import com.example.alex.pollsui.data.Question
import com.example.alex.pollsui.data.source.PollsDataSource

class AddPollPresenter(private val pollsRepository: PollsDataSource, private val addPollView: AddPollContract.View)
    : AddPollContract.Presenter {

    private var questions: MutableList<Question> = ArrayList()

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
        val answrs = processAnswers(answers)
        if (title.isBlank() || answrs.size < 2) {
            addPollView.showInvalidQuestionError()
        } else {
            val question = Question(title)
            question.answers.addAll(answrs)
            questions.add(question)
            addPollView.showQuestion(question)
        }
    }

    override fun savePoll() {
        val title = addPollView.getTitle()
        if (title.isBlank() || questions.isEmpty()) {
            addPollView.showInvalidPollError()
        } else {
            val poll = Poll(title, "user1") // FIXME
            poll.questions.addAll(questions)
            addPollView.setLoadingIndicator(true)
            pollsRepository.createPoll(poll, object : PollsDataSource.CreatePollCallback {
                override fun onResult(successful: Boolean) {
                    addPollView.showPollsList()
                }
            })
        }
    }

    private fun processAnswers(answers: List<String>): List<Answer> {
        val res = ArrayList<Answer>()
        answers.forEach {
            if (it.isNotBlank()) {
                res.add(Answer(it))
            }
        }
        return res
    }
}
