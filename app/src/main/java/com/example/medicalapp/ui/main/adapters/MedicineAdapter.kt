package com.example.medicalapp.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalapp.R
import com.example.medicalapp.databinding.ItemLayoutMedicineRowBinding
import com.example.medicalapp.ui.main.model.AssociatedDrugItem

class MedicineAdapter(private val onClick : (AssociatedDrugItem) -> Unit) : ListAdapter<AssociatedDrugItem, MedicineAdapter.ItemViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemLayoutMedicineRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind()

    inner class ItemViewHolder(private val binding: ItemLayoutMedicineRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val item = getItem(adapterPosition)

            binding.clRoot.setOnClickListener {
                onClick(item)
            }

            binding.tvLogo.text =
                if (item.name?.length ?: 0 > 1) item.name?.subSequence(0, 1).toString()
                    .uppercase() else item.name

            binding.tvName.text = item.name.let {
                try {
                    it?.subSequence(0,1).toString().uppercase() + it?.subSequence(1,it.length).toString()
                } catch (e: Exception) {
                    ""
                }
            }

            binding.tvDose.text = if (item.dose.isNullOrBlank()) "0" else item.dose

            binding.tvStrength.text =
                "${binding.tvStrength.context.getString(R.string.strength)} ${item.strength ?: ""}"
        }
    }

    companion object : DiffUtil.ItemCallback<AssociatedDrugItem>() {
        override fun areItemsTheSame(
            oldItem: AssociatedDrugItem,
            newItem: AssociatedDrugItem
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: AssociatedDrugItem,
            newItem: AssociatedDrugItem
        ): Boolean {
            return oldItem.strength == newItem.strength
        }

    }

}