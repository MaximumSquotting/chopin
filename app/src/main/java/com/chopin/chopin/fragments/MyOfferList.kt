package com.chopin.chopin.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bindView

import com.chopin.chopin.API.API
import com.chopin.chopin.R
import com.chopin.chopin.adapters.RVAdapter
import com.chopin.chopin.models.Offer

import java.util.ArrayList

import butterknife.ButterKnife
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOfferList : Fragment() {

    private var _api: API.APIInterface? = null
    private var offers: ArrayList<Offer>? = null
    private var adapter: RVAdapter? = null
    internal val mRecyclerView: RecyclerView by bindView(R.id.me_offer_list)
    val swipe : SwipeRefreshLayout by bindView(R.id.swipe)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _api = API.getClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_me_offer_list, container, false)
        ButterKnife.bind(this, v)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        get()

        swipe?.setOnRefreshListener {
            Log.d("onRefresh", "calling get()")
            get()
            adapter!!.notifyDataSetChanged()
            swipe!!.isRefreshing = false
        }
    }

    fun get() {
        val query = _api!!.myOffers
        offers = ArrayList<Offer>()
        query.enqueue(object : Callback<List<Offer>> {

            override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                if (response.isSuccessful) {
                    Log.d("onResponse", "got " + response.body().size)
                    offers!!.addAll(response.body())
                    adapter = RVAdapter(offers, activity)
                    mRecyclerView!!.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                Snackbar.make(view!!, "Connection problem", Snackbar.LENGTH_INDEFINITE).show()
            }
        })
        mRecyclerView?.setHasFixedSize(true)
        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(this.context)
        mRecyclerView?.layoutManager = mLayoutManager
    }
}// Required empty public constructor
