package com.masharo.habits.support

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.masharo.habits.screens.listHabit.HabitListViewModel
import com.masharo.habits.screens.habit.HabitViewModel
import com.masharo.habits.databinding.FragmentHabitBinding
import com.masharo.habits.databinding.FragmentHabitListBinding

object InitViewModel {

    fun instanceHabitViewModel(vmOwner: ViewModelStoreOwner,
                               bind: FragmentHabitBinding,
                               id: Int?,
                               resultLauncher: ActivityResultLauncher<Intent>,
                               childFragmentManager: FragmentManager
    ): HabitViewModel {

        val viewModel = ViewModelProvider(vmOwner)[HabitViewModel::class.java]
        viewModel.instance(bind, id, resultLauncher, childFragmentManager)

        return viewModel
    }

    fun instanceHabitListViewModel(vmOwner: ViewModelStoreOwner,
                                   bind: FragmentHabitListBinding,
                                   owner: LifecycleOwner,
                                   type: Int?
    ): HabitListViewModel {

        val viewModal = ViewModelProvider(vmOwner)[HabitListViewModel::class.java]
        viewModal.instance(bind, owner, type)

        return viewModal
    }
}