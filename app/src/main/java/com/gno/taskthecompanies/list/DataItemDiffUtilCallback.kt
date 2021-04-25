package com.gno.taskthecompanies.list

import androidx.recyclerview.widget.DiffUtil
import com.gno.taskthecompanies.retrofit.data.Company

class DataItemDiffUtilCallback : DiffUtil.ItemCallback<Company>() {

    override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean =
        oldItem == newItem

}