package com.example.medicalapp.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import com.example.medicalapp.R
import com.example.medicalapp.databinding.ActivityLoginBinding
import com.example.medicalapp.ui.main.MainActivity
import com.example.medicalapp.ui.auth.viewmodel.LoginViewModel
import com.example.medicalapp.ui.base.BaseActivity
import com.example.medicalapp.utils.getGreetingMessage
import com.example.medicalapp.utils.loginData
import com.example.medicalapp.utils.rColor
import com.example.medicalapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel>()

    override fun viewBinding() =
        ActivityLoginBinding.inflate(layoutInflater).apply { binding = this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(loginData() != null) {
            startActivity(Intent(mContext, MainActivity::class.java))
            finish();
        }

        initCallback()

        initAction()
    }

    private fun initCallback() {

        binding.btnLogin.setOnClickListener {
            if (binding.btnLogin.text.toString().lowercase() == getString(R.string.login).lowercase()) {
                viewModel.login(
                    binding.tieEmailId.text.toString(),
                    binding.tiePassword.text.toString()
                )
            } else {
                viewModel.register(
                    binding.tieEmailId.text.toString(),
                    binding.tiePassword.text.toString()
                )
            }
        }

        binding.lbCreateNew.setOnClickListener {
            if (binding.btnLogin.text.toString().lowercase() == getString(R.string.login).lowercase()) {
                changeNewMemberText(false)
            } else {
                changeNewMemberText()
            }
        }

        viewModel.emailErrorLiveData.observe(this) {
            binding.tilEmailId.error = if (it == -1) null else getString(it)
        }

        viewModel.passwordErrorLiveData.observe(this) {
            binding.tilPassword.error = if (it == -1) null else getString(it)
        }

        viewModel.loginResponse.observe(this) {
            it?.let {
                loginData(it)
                startActivity(Intent(mContext, MainActivity::class.java))
                finish()
            } ?: kotlin.run {
                binding.root.showSnackBar(getString(R.string.username_and_password_wrong))
            }
        }

        viewModel.registerResponse.observe(this) {
            if(it){
                changeNewMemberText(true)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initAction() {

        //Set Greetings
        val spannableGreeting =
            SpannableString("${getString(R.string.hey)}, ${getGreetingMessage()}")
        spannableGreeting.setSpan(
            ForegroundColorSpan(rColor(R.color.color_blue)),
            5,
            spannableGreeting.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.lbGreeting.text = spannableGreeting


        //Set New Member
        changeNewMemberText()


    }

    private fun changeNewMemberText(isNew: Boolean = true) {
        val spannableCreateNew = if (isNew) {
            binding.btnLogin.text = getString(R.string.login)
            SpannableString(getString(R.string.you_are_not_a_member_create_new)).let {
                it.setSpan(
                    ForegroundColorSpan(rColor(R.color.color_blue)),
                    22,
                    it.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                it
            }
        } else {
            binding.btnLogin.text = getString(R.string.register)
            SpannableString(getString(R.string.already_member_login)).let {
                it.setSpan(
                    ForegroundColorSpan(rColor(R.color.color_blue)),
                    17,
                    it.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                it
            }
        }
        binding.lbCreateNew.text = spannableCreateNew
    }


}