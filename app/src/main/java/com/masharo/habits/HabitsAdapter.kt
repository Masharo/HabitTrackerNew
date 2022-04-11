package com.masharo.habits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.masharo.habits.databinding.HabitItemBinding

class HabitsAdapter(
    private val context: Context,
    private val habits: LiveData<List<Habit>>,
    private val onClick: (id: Int) -> Unit):
    RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {

    class HabitsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val bind: HabitItemBinding = HabitItemBinding.bind(itemView)
        var id: Int? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)

        return HabitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {

        habits.value?.let { it ->
            val habit: Habit = it[position]
            val priority = context.resources.getStringArray(R.array.habit_priority)[habit.priority]
            val period = context.resources.getStringArray(R.array.habit_period)[habit.period]

            holder.bind.textViewHabitItemTitle.text = habit.title
            holder.bind.textViewHabitItemPriority.text = context.getString(
                R.string.textView_habitItem_priority,
                priority.lowercase()
            )
            holder.bind.textViewHabitItemCycle.text = context.getString(
                R.string.textView_habitItem_period,
                period,
                habit.countDone,
                habit.count
            )

            holder.bind.imageViewHabitItemType.setImageResource(Habit.getIdImage(habit.type))

            habit.color?.let {
                holder.bind.cardViewHabitItemRoot.background = it.toDrawable()
                holder.bind.imageButtonHabitItemOpenDescription.background = it.toDrawable()
            }

            holder.bind.imageButtonHabitItemOpenDescription.setOnClickListener {
                if (holder.bind.textViewHabitItemDescription.visibility == View.GONE) {
                    holder.bind.textViewHabitItemDescription.visibility = View.VISIBLE
                } else {
                    holder.bind.textViewHabitItemDescription.visibility = View.GONE
                }
            }

            holder.bind.textViewHabitItemDescription.text = habit.description

            holder.itemView.setOnClickListener {
                onClick(position)
            }
        }
    }

    override fun getItemCount() = habits.value?.size ?: 0
}