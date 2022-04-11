package com.masharo.habits

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masharo.habits.databinding.ActivityHabitsListBinding

class HabitsListActivity : AppCompatActivity() {

    private lateinit var bind: ActivityHabitsListBinding
    private lateinit var viewModel: HabitListViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHabitsListBinding.inflate(layoutInflater)
        setContentView(bind.root)

        viewModel = ViewModelProvider(this)[HabitListViewModel::class.java]

        bind.recyclerViewHabitsListHabits.layoutManager = LinearLayoutManager(applicationContext)
        bind.recyclerViewHabitsListHabits.adapter =
            HabitsAdapter(applicationContext, viewModel.getHabits()) {
                val intent = Intent(this, HabitActivity::class.java)
                intent.putExtra(HabitActivity.ID, it)
                startActivity(intent)
            }

        bind.fabHabitsListAdd.setOnClickListener {
            val intent = Intent(applicationContext, HabitActivity::class.java)
            startActivity(intent)
        }

        viewModel.getHabits().observe(this) {
            bind.recyclerViewHabitsListHabits.adapter?.notifyItemInserted(0)
        }

        ItemTouchHelper(HabitCallback(viewModel, bind)).attachToRecyclerView(bind.recyclerViewHabitsListHabits)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()

        if (HabitActivity.isUpdate()) {
            bind.recyclerViewHabitsListHabits.adapter?.notifyDataSetChanged()
        }
    }

    private class HabitCallback(
        private val viewModel: HabitListViewModel,
        private val bind: ActivityHabitsListBinding
    ): ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            bind.recyclerViewHabitsListHabits.adapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            viewModel.swapHabit(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.removeHabit(viewHolder.adapterPosition)
            bind.recyclerViewHabitsListHabits.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
        }

    }
}