package com.example.medicalapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicalapp.R
import com.example.medicalapp.databinding.ActivityMainBinding
import com.example.medicalapp.ui.base.BaseActivity
import com.example.medicalapp.ui.details.DetailActivity
import com.example.medicalapp.ui.main.adapters.MedicineAdapter
import com.example.medicalapp.ui.main.model.AssociatedDrugItem
import com.example.medicalapp.ui.main.viewmodels.MainViewModel
import com.example.medicalapp.utils.getGreetingMessage
import com.example.medicalapp.utils.loginData
import com.example.medicalapp.utils.rColor
import com.example.medicalapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private var adapter : MedicineAdapter? = null

    override fun viewBinding() =
        ActivityMainBinding.inflate(layoutInflater).apply { binding = this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCallback()

        initAction()
    }

    private fun initCallback() {

        viewModel.errorResponse.observe(this) {
            binding.root.showSnackBar(it)
        }

        viewModel.loadingProgress.observe(this) {
           binding.progressBar.isVisible = it
        }

        viewModel.response.observe(this) {
            adapter?.submitList(it)
        }

    }

    private fun initAction() {

        loginData()?.let {
            binding.lbName.text = it.email
        }

        //Set greeting
        val spannableGreeting =
            SpannableString("${getString(R.string.hey)}, ${getGreetingMessage()}")
        spannableGreeting.setSpan(
            ForegroundColorSpan(rColor(R.color.color_blue)),
            5,
            spannableGreeting.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.lbGreeting.text = spannableGreeting

        initRecyclerView()
    }

    private fun initRecyclerView() {

        binding.rvList.layoutManager = LinearLayoutManager(mContext)
        binding.rvList.itemAnimator = DefaultItemAnimator()
        adapter = MedicineAdapter {
            startActivity(Intent(mContext,DetailActivity::class.java).putExtra("model",it))
        }
        binding.rvList.adapter = adapter
    }

}