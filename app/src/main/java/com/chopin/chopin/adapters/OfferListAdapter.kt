package com.chopin.chopin.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chopin.chopin.R
import com.chopin.chopin.models.Offer

class OfferListAdapter(private val offers: MutableList<Offer>) : RecyclerView.Adapter<OfferListAdapter.APIHolder>() {

    fun clear() {
        offers.clear()
        notifyDataSetChanged()
    }

    fun addAll(offers: List<Offer>) {
        this.offers.addAll(offers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): APIHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_offer_list, parent, false)
        return APIHolder(view)
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    override fun onBindViewHolder(offerViewHolder: APIHolder, position: Int) {
    }

    class APIHolder(view: View) : RecyclerView.ViewHolder(view)
}
