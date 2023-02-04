package com.example.winningmindset.di

import android.app.Application
import androidx.room.Room
import com.example.winningmindset.feature_goals.data.data_source.GoalDatabase
import com.example.winningmindset.feature_goals.data.repository.GoalRepositoryImpl
import com.example.winningmindset.feature_goals.data.repository.MilestoneRepositoryImpl
import com.example.winningmindset.feature_goals.domain.repository.GoalRepository
import com.example.winningmindset.feature_goals.domain.repository.MilestoneRepository
import com.example.winningmindset.feature_goals.domain.use_case.AddGoal
import com.example.winningmindset.feature_goals.domain.use_case.AddMilestoneList
import com.example.winningmindset.feature_goals.domain.use_case.DeleteGoal
import com.example.winningmindset.feature_goals.domain.use_case.GetGoalWithMileStones
import com.example.winningmindset.feature_goals.domain.use_case.GetGoalsWithMilestones
import com.example.winningmindset.feature_goals.domain.use_case.GoalUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGoalDatabase(app: Application): GoalDatabase {
        return Room.databaseBuilder(
            app,
            GoalDatabase::class.java,
            GoalDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideGoalRepository(db: GoalDatabase): GoalRepository{
        return GoalRepositoryImpl(db.goalDao)
    }

    @Provides
    @Singleton
    fun provideMilestoneRepository(db: GoalDatabase): MilestoneRepository{
        return MilestoneRepositoryImpl(db.milestoneDao)
    }

    @Provides
    @Singleton
    fun provideGoalUseCases(goalRepository: GoalRepository, milestoneRepository: MilestoneRepository): GoalUseCases {
        return GoalUseCases(
            getGoalsWithMilestones = GetGoalsWithMilestones(goalRepository),
            getGoalWithMilestones = GetGoalWithMileStones(goalRepository),
            addGoal = AddGoal(goalRepository),
            deleteGoal = DeleteGoal(goalRepository),
            addMilestoneList = AddMilestoneList(milestoneRepository)
        )
    }

}