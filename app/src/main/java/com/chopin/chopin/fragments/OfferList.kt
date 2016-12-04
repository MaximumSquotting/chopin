package com.chopin.chopin.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chopin.chopin.API.API
import com.chopin.chopin.R
import com.chopin.chopin.adapters.RVAdapter
import com.chopin.chopin.models.Offer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class OfferList : Fragment() {
    private var _api: API.APIInterface? = null
    private var offers: ArrayList<Offer>? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _api = API.getClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_offer_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val query = _api!!.allOffers
        offers = ArrayList<Offer>()

        query.enqueue(object : Callback<List<Offer>> {

            override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                if (response.isSuccessful) {
                    offers!!.addAll(response.body())
                    val adapter = RVAdapter(offers)
                    mRecyclerView!!.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                Snackbar.make(view!!, "Connection error", Snackbar.LENGTH_INDEFINITE).show()
            }
        })

        mRecyclerView = activity.findViewById(R.id.offer_list) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(this.context)
        mRecyclerView!!.layoutManager = mLayoutManager
    }

}// Required empty public constructor


