package com.masharo.habits.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masharo.habits.R
import com.masharo.habits.databinding.HabitItemBinding
import com.masharo.habits.presentation.model.HabitPresentation

class HabitsAdapter(
    var habits: List<HabitPresentation>? = null,
    private val onClickItem: (id: Int) -> Unit,
    private val onClickDone: (habit: HabitPresentation) -> Unit
): RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {

    class HabitsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val bind: HabitItemBinding = HabitItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {

        habits?.let {
            val habit: HabitPresentation = it[position]
            holder.bind.habit = habit
            holder.bind.holder = holder

            holder.itemView.setOnClickListener {
                habit.id?.let {
                    onClickItem(it)
                }
            }

            holder.bind.imageButtonHabitItemOpenDescription.setOnClickListener {
                habit.id?.let {
                    onClickDone(habit)
                }
            }

        }

    }

    override fun getItemCount() = habits?.size ?: 0
}