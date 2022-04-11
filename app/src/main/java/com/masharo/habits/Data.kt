package com.masharo.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Data {
    private val list: MutableList<Habit> = mutableListOf()
    private val data: MutableLiveData<List<Habit>> = MutableLiveData(list)

    fun add(habit: Habit) {
        list.reverse()
        list.add(habit)
        data.value = list
        list.reverse()
    }

    fun data(): LiveData<List<Habit>> = data

    fun get(id: Int) = data.value?.get(id)

    fun set(habit: Habit, id: Int) {
        list[id] = habit
    }

    fun remove(id: Int) {
        list.removeAt(id)
    }

    fun swap(id1: Int, id2: Int) {
        val habit1 = list[id1]
        list[id1] = list[id2]
        list[id2] = habit1
    }
}
