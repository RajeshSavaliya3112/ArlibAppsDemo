package com.example.medicalapp.ui.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.medicalapp.R
import com.example.medicalapp.databinding.ActivityDetailBinding
import com.example.medicalapp.ui.base.BaseActivity
import com.example.medicalapp.ui.main.model.AssociatedDrugItem

class DetailActivity : BaseActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun viewBinding(): ViewBinding = ActivityDetailBinding.inflate(layoutInflater).apply {
        binding = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActions()
    }

    private fun initActions() {

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        val item = intent?.getSerializableExtra("model") as AssociatedDrugItem

        binding.tvDetailsName.text = item.name.let {
            try {
                it?.subSequence(0,1).toString().uppercase() + it?.subSequence(1,it.length).toString()
            } catch (e: Exception) {
                ""
            }
        }
        binding.tvDetailsStrength.text = item.strength ?: ""
        binding.tvdetailsDose.text = if (item.dose.isNullOrBlank()) "0" else item.dose

    }

}