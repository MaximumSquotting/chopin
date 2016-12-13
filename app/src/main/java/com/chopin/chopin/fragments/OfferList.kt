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
import com.chopin.chopin.adapters.OfferListAdapter
import com.chopin.chopin.models.Offer
import java.util.*

class OfferList : Fragment() {
    private var offers: ArrayList<Offer>? = null
    val swipe: SwipeRefreshLayout? by bindView(R.id.swipe)
    internal val mRecyclerView: RecyclerView by bindView(R.id.offer_list)
    private var adapter:OfferListAdapter? = null
    private var TAG: String = " OfferList";
    private var connectionHandler: ConnectionHandler = ConnectionHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        offers = connectionHandler.allOfferFromServer
        adapter = OfferListAdapter(offers, activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_offer_list, container, false)
        ButterKnife.bind(this, v)

        offers = connectionHandler.allOfferFromServer
        adapter!!.notifyDataSetChanged()
        return v;
    }

    override fun onStart() {
        super.onStart()

        mRecyclerView!!.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this.context)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        offers = connectionHandler.allOfferFromServer
        adapter!!.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        offers = connectionHandler.allOfferFromServer
        adapter!!.notifyDataSetChanged()

        swipe?.setOnRefreshListener({
            Log.d("onRefresh", "calling get()")
            offers = connectionHandler.allOfferFromServer
            adapter!!.notifyDataSetChanged()
            swipe!!.isRefreshing = false
        })

    }

}// Required empty public constructor


