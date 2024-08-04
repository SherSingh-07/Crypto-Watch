package com.example.cryptowatch

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.cryptowatch.adapter.MarketAdapter
import com.example.cryptowatch.apis.ApiInterface
import com.example.cryptowatch.apis.ApiUtilities
import com.example.cryptowatch.databinding.ActivityWatchlistBinding
import com.example.cryptowatch.models.CryptoCurrency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class watchlist_activity : AppCompatActivity() {
    private lateinit var binding: ActivityWatchlistBinding
    private lateinit var watchList : ArrayList<String>
    private lateinit var watchListItem: ArrayList<CryptoCurrency>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWatchlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readData()

        lifecycleScope.launch(Dispatchers.IO){
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java)
                .getMarketData()

            if (res.body()!=null){

                withContext(Dispatchers.Main){
                    watchListItem = ArrayList()
                    watchListItem.clear()

                    for (watchData in watchList){
                        for (item in res.body()!!.data.cryptoCurrencyList){
                            if (watchData==item.symbol){
                                watchListItem.add(item)
                            }
                        }
                    }
                    binding.watchlistRecyclerView.adapter= MarketAdapter(this@watchlist_activity,watchListItem)
                }

            }
        }


    }
    private fun readData() {
        val sharedPreferences = this.getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson= Gson()
        val json = sharedPreferences.getString("watchlist",ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>(){}.type
        watchList = gson.fromJson(json , type)
    }
}