package com.example.alex.pollsui

import com.example.alex.pollsui.data.source.PollsRepository
import com.example.alex.pollsui.data.source.local.PollsFakeDataSource
import com.example.alex.pollsui.data.source.remote.PollsRemoteDataSource

object Injection {

    fun providePollsRepository(): PollsRepository {
        return PollsRepository.getInstance(PollsRemoteDataSource.getInstance(), PollsFakeDataSource.getInstance())
    }
}
