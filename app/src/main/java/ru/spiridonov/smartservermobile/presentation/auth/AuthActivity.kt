package ru.spiridonov.smartservermobile.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.databinding.ActivityAuthBinding
import ru.spiridonov.smartservermobile.presentation.ViewModelFactory
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as SmartServerApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        btnSignListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginStatus.observe(this) {
            if (it) {
                Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
                finish()
            } else
                Toast.makeText(this, "Ошибка при входе", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnSignListener() {
        binding.login.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, AuthActivity::class.java)
    }
}