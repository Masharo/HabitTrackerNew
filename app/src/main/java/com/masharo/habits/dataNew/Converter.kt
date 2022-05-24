package com.masharo.habits.dataNew

import com.masharo.habits.dataNew.model.local.HabitData
import com.masharo.habits.dataNew.model.local.IdData
import com.masharo.habits.dataNew.model.remote.HabitRemote
import com.masharo.habits.dataNew.model.remote.ParamHabitPutRemote
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id

fun domainToDataHabit(habit: Habit) = HabitData(
        id = habit.id,
        idRemote = habit.idRemote,
        dateRemote = habit.dateRemote,
        title = habit.title,
        description = habit.description,
        priority = habit.priority,
        type = habit.type,
        count = habit.count,
        period = habit.period,
        countDone = habit.countDone,
        color = habit.color
    )

fun dataToDomainHabit(habitData: HabitData) = Habit(
        id = habitData.id,
        idRemote = habitData.idRemote,
        dateRemote = habitData.dateRemote,
        title = habitData.title,
        description = habitData.description,
        priority = habitData.priority,
        type = habitData.type,
        count = habitData.count,
        period = habitData.period,
        countDone = habitData.countDone,
        color = habitData.color
    )

fun remoteToDataHabit(habitRemote: HabitRemote) = HabitData(
        idRemote = habitRemote.questId,
        dateRemote = habitRemote.questDate,
        title = habitRemote.questTitle,
        description = habitRemote.questDescription,
        priority = habitRemote.questPriority,
        type = habitRemote.questType,
        count = habitRemote.questCount,
        period = habitRemote.questFrequency,
        countDone = habitRemote.questDoneDates.size,
        color = habitRemote.questColor
    )

fun dataToRemoteHabit(habitData: HabitData) = HabitRemote(
        questId = habitData.idRemote ?: "",
        questTitle = habitData.title,
        questDescription = habitData.description,
        questCount = habitData.count,
        questDoneDates = arrayListOf(),
        questFrequency = habitData.period,
        questPriority = habitData.priority,
        questType = habitData.type,
        questDate = habitData.dateRemote,
        questColor = habitData.color
    )

fun domainToRemoteHabit(habit: Habit) = HabitRemote(
        questId = habit.idRemote ?: "",
        questTitle = habit.title,
        questDescription = habit.description,
        questCount = habit.count,
        questDoneDates = arrayListOf(),
        questFrequency = habit.period,
        questPriority = habit.priority,
        questType = habit.type,
        questDate = habit.dateRemote,
        questColor = habit.color
   )

fun domainHabitToParamPutRemote(habit: Habit) = ParamHabitPutRemote(
        idData = habit.id,
        idRemote = habit.idRemote
   )

fun domainToDataId(id: Id) = IdData(
        id = id.id
   )

fun dataToDomainId(id: IdData) = Id(
        id = id.id
   )