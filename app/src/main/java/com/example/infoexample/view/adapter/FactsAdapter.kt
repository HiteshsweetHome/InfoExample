package com.example.infoexample.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.infoexample.R
import com.example.infoexample.databinding.RowFactsItemBinding
import com.example.infoexample.model.FactsModel


class FactsAdapter : RecyclerView.Adapter<FactsAdapter.MyViewHolder>() {

    var mList: List<FactsModel> = ArrayList()

    fun setProductList(earningModels: List<FactsModel>) {

        if (mList.isNullOrEmpty()) {
            mList = earningModels
           notifyItemRangeInserted(0, earningModels.size)
        } else {
            var result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int {
                        return mList.size
                }

                override fun getNewListSize(): Int {
                    return earningModels.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return  mList[oldItemPosition].title == earningModels[newItemPosition].title
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    var newProduct = earningModels[newItemPosition]
                    var oldProduct = mList[oldItemPosition]
                    return newProduct.title == oldProduct?.title
                }
            })
            mList = earningModels
            result.dispatchUpdatesTo(this)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding: RowFactsItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_facts_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var factsModel :  FactsModel = mList[position]
        holder.bind(factsModel)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    inner class MyViewHolder(val binding: RowFactsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(factsModel :  FactsModel){
            binding.factsModel = factsModel
            binding.executePendingBindings()
        }
    }
}
