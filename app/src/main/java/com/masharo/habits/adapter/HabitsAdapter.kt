package com.masharo.habits.adapter

import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.masharo.habits.R
import com.masharo.habits.databinding.HabitItemBinding
import com.masharo.habits.presentation.model.HabitPresentation

class HabitsAdapter(
    var habits: List<HabitPresentation>? = null,
    private val onClickItem: (id: Int) -> Unit,
    private val onClickDone: (habit: HabitPresentation) -> Unit,
    private val onClickDelete: (id: Int) -> Unit
): RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {

    class HabitsViewHolder(
        itemView: View,
        private val onClickDelete: (id: Int) -> Unit,
        var id: Int? = null
    ): RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        val bind: HabitItemBinding = HabitItemBinding.bind(itemView)

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            v?.id?.let {
                menu?.add(Menu.NONE, it, Menu.NONE, "Remove")?.setOnMenuItemClickListener {
                    id?.let {
                        onClickDelete(it)
                    }

                    return@setOnMenuItemClickListener true
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitsViewHolder(view, onClickDelete)
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {

        habits?.let {
            val habit: HabitPresentation = it[position]
            holder.bind.habit = habit
            holder.bind.holder = holder
            holder.id = habit.id

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