package ru.spiridonov.smartservermobile.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.spiridonov.smartservermobile.R
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.databinding.ActivityMainBinding
import ru.spiridonov.smartservermobile.presentation.auth.AuthActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as SmartServerApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.init()
    }

    private fun observeViewModel() {
        viewModel.mainActivityState.observe(this) { state ->
            when (state) {
                is MainActivityState.NeedToReLogin -> {
                    AlertDialog.Builder(this)
                        .setTitle("Внимание")
                        .setMessage("Необходимо войти в аккаунт")
                        .setPositiveButton("Ок") { _, _ ->
                            val intent = AuthActivity.newIntent(this)
                            startActivity(intent)
                        }
                        .setCancelable(false)
                        .show()
                }

                is MainActivityState.SetupView -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        checkNotificationPermission()
                    createScreen()
                }
            }
        }
    }

    private fun createScreen() {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun changeProgressBarState(turnOn: Boolean = true) {
        binding.pbLoading.isGone = !turnOn
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                ),
                0
            )
        }
    }
}