package com.masharo.habits.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.masharo.habits.R
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.databinding.HabitItemBinding

class HabitsAdapter(
    var habits: List<Habit>?,
    private val onClick: (id: Int) -> Unit):
    RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {

    class HabitsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var isDescriptionOpen = ObservableBoolean(false)
        val bind: HabitItemBinding = HabitItemBinding.bind(itemView)

        fun descriptionOpenChange() {
            isDescriptionOpen.set(!isDescriptionOpen.get())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {

        habits?.let {
            val habit: Habit = it[position]
            holder.bind.habit = habit
            holder.bind.holder = holder

            holder.itemView.setOnClickListener {
                habit.id?.let {
                    onClick(it)
                }
            }

        }

    }

    override fun getItemCount() = habits?.size ?: 0
}