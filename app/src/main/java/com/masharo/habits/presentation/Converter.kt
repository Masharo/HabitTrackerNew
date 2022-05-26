package com.masharo.habits.presentation

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import com.masharo.habits.presentation.model.HabitPresentation

fun domainToPresentationHabit(habit: Habit) = HabitPresentation()
    .apply {
        id = habit.id
        title = habit.title
        idRemote = habit.idRemote ?: ""
        description = habit.description
        priority = habit.priority
        type = habit.type
        color = habit.color
        count = habit.count
        countDone = habit.countDone
    }

fun presentationToDomainHabit(habitPresentation: HabitPresentation) = Habit(
    id = habitPresentation.id,
    idRemote = habitPresentation.idRemote,
    dateRemote = habitPresentation.dateRemote,
    title = habitPresentation.title,
    description = habitPresentation.description,
    period = habitPresentation.period,
    priority = habitPresentation.priority,
    type = habitPresentation.type,
    color = habitPresentation.color,
    count = habitPresentation.count,
    countDone = habitPresentation.countDone
)

fun presentationToDomainId(id: Int) = Id(
    id = id
)