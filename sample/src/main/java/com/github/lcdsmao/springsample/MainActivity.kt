package com.github.lcdsmao.springsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.lcdsmao.springsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    navController = findNavController(R.id.nav_host_fragment)
    binding.navView.setupWithNavController(navController)
    setSupportActionBar(binding.toolbar)
    val appBarConfiguration = AppBarConfiguration(
      setOf(
        R.id.dragSampleFragment,
        R.id.simpleSampleFragment
      ),
      binding.drawerLayout
    )
    binding.toolbar.setupWithNavController(navController, appBarConfiguration)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }
}
