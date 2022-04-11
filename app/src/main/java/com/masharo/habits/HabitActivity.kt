package com.masharo.habits

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.masharo.habits.databinding.ActivityHabitBinding


class HabitActivity : AppCompatActivity() {

    private lateinit var bind: ActivityHabitBinding
    private lateinit var viewModel: HabitViewModel
    private var color: Int? = null
    private var id: Int? = null
    private var countDone = 0
    private val countDoneStartValue = 0

    companion object {
        const val ID: String = "ID"
        private var isUpdate: Boolean = false

        fun isUpdate(): Boolean {
            val result = isUpdate
            isUpdate = false

            return result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHabitBinding.inflate(layoutInflater)
        setContentView(bind.root)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        bind.radioGroupHabitType.setOnCheckedChangeListener { _, id ->
            bind.imageViewHabitType.setImageResource(Habit.getIdImage(id))
        }

        bind.spinnerHabitPriority.adapter = ArrayAdapter(
            applicationContext,
            R.layout.habit_spinner_item,
            resources.getStringArray(R.array.habit_priority)
        )

        bind.spinnerHabitPeriod.adapter = ArrayAdapter(
            applicationContext,
            R.layout.habit_spinner_item,
            resources.getStringArray(R.array.habit_period)
        )

        bind.buttonHabitSave.setOnClickListener {
            id?.let {
                viewModel.setHabit(getHabit(), it)
                isUpdate = true
            } ?: viewModel.addHabit(getHabit())

            finish()
        }

        bind.imageButtonHabitDoneInc.setOnClickListener {
            instanceCountDone()

            countDone++
            bind.editTextHabitDone.setText(countDone.toString())
        }

        bind.imageButtonHabitDoneDec.setOnClickListener {
            instanceCountDone()

            if (countDone > countDoneStartValue) {
                countDone--
            }

            if (countDone == countDoneStartValue) {
                bind.editTextHabitDone.setText("")
            } else {
                bind.editTextHabitDone.setText(countDone.toString())
            }

        }

        //////
        bind.buttonHabitColorPicker.setOnClickListener {
            val intent = Intent(this, ColorPickerActivity::class.java)
            startActivityForResult(intent, ColorPickerActivity.ID_ACTIVITY)
        }

        intentLoader()
    }

    private fun instanceCountDone() {
        val countDoneString = bind.editTextHabitDone.text.toString()

        countDone =
            if (countDoneString.isNotEmpty()) {
                countDoneString.toInt()
            } else {
                countDoneStartValue
            }
    }

    private fun intentLoader() {
        if (intent.hasExtra(ID)) {
            id = intent.getIntExtra(ID, 0)
            id?.let {
                viewModel.getHabit(it)?.let { habit ->
                    setHabit(habit)
                }

                bind.textViewHabitTitle.text = getString(R.string.textView_habit_titleOld)
            }
        }
    }

    private fun setHabit(habit: Habit) {
        bind.editTextHabitTitle.setText(habit.title)
        bind.editTextHabitDescription.setText(habit.description)
        bind.spinnerHabitPriority.setSelection(habit.priority)
        bind.radioGroupHabitType.check(habit.type)
        bind.spinnerHabitPeriod.setSelection(habit.period)

        val countDoneStr = habit.countDone.toString()
        val countStr = habit.count.toString()
        val zeroValue = "0"

        if (countDoneStr != zeroValue) {
            bind.editTextHabitDone.setText(countDoneStr)
        }

        if (countDoneStr != zeroValue) {
            bind.editTextHabitCount.setText(countStr)
        }

        countDone = habit.countDone
    }

    private fun getHabit(): Habit {
        instanceCountDone()

        return Habit(
            bind.editTextHabitTitle.text.toString(),
            bind.editTextHabitDescription.text.toString(),
            bind.spinnerHabitPriority.selectedItemPosition,
            bind.radioGroupHabitType.checkedRadioButtonId,
            when (val countStr = bind.editTextHabitCount.text.toString()) {
                "" -> 0
                else -> countStr.toInt()
            },
            bind.spinnerHabitPeriod.selectedItemPosition,
            countDone,
            color
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ColorPickerActivity.ID_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    color = it.getIntExtra(ColorPickerActivity.COLOR, -1026)
                }
            }
        }
    }
}