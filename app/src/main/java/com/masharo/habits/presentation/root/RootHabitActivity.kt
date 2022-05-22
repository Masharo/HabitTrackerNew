package com.masharo.habits.presentation.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.masharo.habits.R
import com.masharo.habits.databinding.ActivityRootHabitBinding

class RootHabitActivity : AppCompatActivity() {

    private lateinit var bind: ActivityRootHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRootHabitBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.navigationViewRoot.menu.findItem(R.id.item_menuDrawer_info).setOnMenuItemClickListener {
            bind.navHostFragment.findNavController().navigate(R.id.infoFragment)
            return@setOnMenuItemClickListener true
        }

        bind.navigationViewRoot.menu.findItem(R.id.item_menuDrawer_home).setOnMenuItemClickListener {
            bind.navHostFragment.findNavController().navigate(R.id.viewPagerHabitListFragment)
            return@setOnMenuItemClickListener true
        }

    }
}