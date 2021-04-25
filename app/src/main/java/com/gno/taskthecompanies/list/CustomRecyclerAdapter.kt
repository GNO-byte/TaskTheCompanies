package com.gno.taskthecompanies.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gno.taskthecompanies.R
import com.gno.taskthecompanies.retrofit.Api
import com.gno.taskthecompanies.retrofit.data.Company
import com.squareup.picasso.Picasso


class CustomRecyclerAdapter(
    private val ImageScreenWidth: Int,
    private val cellClickListener: (String) -> Unit
) : ListAdapter<Company, CustomRecyclerAdapter.DataHolder>(DataItemDiffUtilCallback()) {

    override fun getItemCount(): Int {
        return when (val count = super.getItemCount()) {
            0 -> 0
            else -> count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {

        val company = getItem(position)

        //companyCard
        holder.companyCard.setOnClickListener {
            cellClickListener.invoke(company.id)
        }

        //companyImage
        holder.companyName.text = company.name

        //companyImage
        Picasso.get().load(Api.BASE_URL + company.img).resize(ImageScreenWidth, ImageScreenWidth)
            .error(R.drawable.ic_blank_company_picture_100)
            .into(holder.companyImage)

    }

    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val companyCard: CardView = itemView.findViewById(R.id.recyclerview_item_cardview)
        val companyImage: ImageView = itemView.findViewById(R.id.recyclerview_item_image)
        val companyName: TextView = itemView.findViewById(R.id.recyclerview_item_name)

    }

}