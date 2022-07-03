package com.masharo.habits.presentation.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.masharo.habits.ui.HabitComposeTheme

class RootHabitActivity : AppCompatActivity() {

//    private lateinit var bind: ActivityRootHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HabitComposeTheme {
                RootScreen()
            }
        }

//        bind = ActivityRootHabitBinding.inflate(layoutInflater)
//        setContentView(bind.root)

//        bind.navigationViewRoot.menu.findItem(R.id.item_menuDrawer_info).setOnMenuItemClickListener {
//            bind.navHostFragment.findNavController().navigate(R.id.infoFragment)
//            return@setOnMenuItemClickListener true
//        }
//
//        bind.navigationViewRoot.menu.findItem(R.id.item_menuDrawer_home).setOnMenuItemClickListener {
//            bind.navHostFragment.findNavController().navigate(R.id.viewPagerHabitListFragment)
//            return@setOnMenuItemClickListener true
//        }

    }
}

@Composable
fun RootScreen() {
    Scaffold {
        Row {
            Text(text = "HELLO COMPOSE!!!!")
        }
    }
}

@Preview(showSystemUi = true, device = Devices.NEXUS_7)
@Composable
fun RootScreenPreview() {
    RootScreen()
}