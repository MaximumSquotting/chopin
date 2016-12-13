package com.chopin.chopin.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bindView
import butterknife.ButterKnife
import com.chopin.chopin.ConnectionHandler
import com.chopin.chopin.R
import com.chopin.chopin.adapters.RVAdapter
import com.chopin.chopin.models.Offer
import java.util.*

class MyOfferList : Fragment() {

    private var offers: ArrayList<Offer>? = null
    private var adapter: RVAdapter? = null
    internal val mRecyclerView: RecyclerView by bindView(R.id.me_offer_list)
    val swipe : SwipeRefreshLayout by bindView(R.id.swipe)
    private var connectionHandler: ConnectionHandler = ConnectionHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offers = ArrayList<Offer>()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_me_offer_list, container, false)
        ButterKnife.bind(this, v)
        return v
    }

    override fun onStart() {
        super.onStart()
        mRecyclerView?.setHasFixedSize(true)
        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(this.context)
        mRecyclerView?.layoutManager = mLayoutManager
        offers = connectionHandler.myOfferFromServer
        adapter = RVAdapter(offers, activity)
        mRecyclerView!!.adapter = adapter
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        swipe?.setOnRefreshListener {
            Log.d("onRefresh", "calling get()")
            offers = connectionHandler.myOfferFromServer
            adapter!!.notifyDataSetChanged()
            swipe!!.isRefreshing = false
        }
    }

}// Required empty public constructor
