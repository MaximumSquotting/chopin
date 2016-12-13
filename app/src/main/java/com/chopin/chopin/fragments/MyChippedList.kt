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

class MyChippedList : Fragment() {

    private var offers: ArrayList<Offer>? = null
    private var mRecyclerView: RecyclerView? = null
    private var connectionHandler: ConnectionHandler = ConnectionHandler()
    val swipe : SwipeRefreshLayout by bindView(R.id.swipe)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionHandler = ConnectionHandler()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_list, container, false)
        ButterKnife.bind(this, v)

        return v
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        offers = ArrayList<Offer>()

        mRecyclerView = activity.findViewById(R.id.list) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(this.context)
        mRecyclerView!!.layoutManager = mLayoutManager
        offers = connectionHandler!!.chippedOffersFromServer
        val adapter = RVAdapter(offers, activity)
        mRecyclerView!!.adapter = adapter

        swipe?.setOnRefreshListener {
            Log.d("onRefresh", "calling get()")
            offers = connectionHandler.myOfferFromServer
            adapter!!.notifyDataSetChanged()
            swipe!!.isRefreshing = false
        }

    }
}// Required empty public constructor
