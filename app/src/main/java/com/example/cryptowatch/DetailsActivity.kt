package com.example.cryptowatch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.cryptowatch.databinding.ActivityDetailsBinding
import com.example.cryptowatch.models.CryptoCurrency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)


        setContentView(binding.root)
        val data = intent.getSerializableExtra("data") as CryptoCurrency?
//        val data: CryptoCurrency? = item.name

        binding.backStackButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        setUpDetails(data)

        loadChart(data)

        setButtonOnClick(data)

        addToWatchList(data)
    }

    var watchList : ArrayList<String>?=null
    var watchListIsChecked = false

    private fun addToWatchList(data: CryptoCurrency?) {
    readData()

        watchListIsChecked = if (watchList!!.contains(data!!.symbol)) {
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
            true
        }else{
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
            false
        }

        binding.addWatchlistButton.setOnClickListener {
            watchListIsChecked=
                if(!watchListIsChecked){
                    if (!watchList!!.contains(data!!.symbol)){
                       watchList!!.add(data!!.symbol)
                    }
                    storeData()
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
                    true
                }else{
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
                    watchList!!.remove(data!!.symbol)
                    storeData()
                    false
                }
        }
        // btn_4 -> click on add favourite button to add favourite list.
        binding.btn4.setOnClickListener {
            watchListIsChecked=
                if(!watchListIsChecked){
                    if (!watchList!!.contains(data!!.symbol)){
                        watchList!!.add(data!!.symbol)
                        Toast.makeText(this,"Add To Favourite List",Toast.LENGTH_SHORT).show()
                    }
                    storeData()
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
                    true
                }else{
                    binding.addWatchlistButton.setImageResource(R.drawable.ic_star_outline)
                    watchList!!.remove(data!!.symbol)
                    Toast.makeText(this,"Removed To Favourite List",Toast.LENGTH_SHORT).show()
                    storeData()
                    false
                }
        }

    }

    private fun storeData(){
        val sharedPreferences = this.getSharedPreferences("watchlist",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson= Gson()
        val json = gson.toJson(watchList)
        editor.putString("watchlist", json)
        editor.apply()
    }

    private fun readData() {
        val sharedPreferences = this.getSharedPreferences("watchlist",Context.MODE_PRIVATE)
        val gson= Gson()
        val json = sharedPreferences.getString("watchlist",ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>(){}.type
        watchList = gson.fromJson(json , type)
    }

    private fun setButtonOnClick(item: CryptoCurrency?) {
        var oneMonth = binding.button
        var oneWeek = binding.button1
        var oneDay = binding.button2
        var fourHour = binding.button3
        var oneHour = binding.button4
        var fifteenMinute = binding.button5

        val clickListener = View.OnClickListener {
            when (it.id) {
                fifteenMinute.id -> loadChartData(it, "15", item, oneDay, oneMonth, oneWeek, fourHour, oneHour)

                oneHour.id -> loadChartData(
                    it,
                    "1H",
                    item,
                    oneDay,
                    oneMonth,
                    oneWeek,
                    fourHour,
                    fifteenMinute
                )

                fourHour.id -> loadChartData(
                    it,
                    "4H",
                    item,
                    oneDay,
                    oneMonth,
                    oneWeek,
                    fifteenMinute,
                    oneHour
                )

                oneDay.id -> loadChartData(
                    it,
                    "D",
                    item,
                    fifteenMinute,
                    oneMonth,
                    oneWeek,
                    fourHour,
                    oneHour
                )

                oneWeek.id -> loadChartData(
                    it,
                    "W",
                    item,
                    oneDay,
                    oneMonth,
                    fifteenMinute,
                    fourHour,
                    oneHour
                )

                oneMonth.id -> loadChartData(
                    it,
                    "M",
                    item,
                    oneDay,
                    fifteenMinute,
                    oneWeek,
                    fourHour,
                    oneHour
                )
            }
        }

        fifteenMinute.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneMonth.setOnClickListener(clickListener)


    }

    private fun loadChartData(

        it: View?,
        s: String,
        item: CryptoCurrency?,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton,
    ) {
        disableButton(oneDay, oneMonth, oneWeek, fourHour, oneHour)
        it!!.setBackgroundResource(R.drawable.active_button)
        binding.detailChartWebView.settings.javaScriptEnabled = true
        binding.detailChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detailChartWebView.loadUrl(

         "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + item!!.symbol
            .toString() + "USD&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg" +
                "=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=" +
                "[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"



        )


    }

    private fun disableButton(
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton,
    ) {

        oneDay.background = null
        oneMonth.background = null
        oneWeek.background = null
        oneHour.background = null
        fourHour.background = null

    }

    private fun loadChart(item: CryptoCurrency?) {
        binding.detailChartWebView.settings.javaScriptEnabled = true
        binding.detailChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detailChartWebView.loadUrl(

                "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + item!!.symbol
                    .toString() + "USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg" +
                        "=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=" +
                        "[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"

        )


    }


    private fun setUpDetails(data: CryptoCurrency?) {

        binding.detailSymbolTextView.text = data!!.symbol

        Glide.with(this).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png"
        ).thumbnail(Glide.with(this).load(R.drawable.spinner))
            .into(binding.detailImageView)

        binding.detailPriceTextView.text = String.format("%.4f", data.quotes[0].price)


        if (data.quotes[0].percentChange24h > 0) {
            binding.detailChangeTextView.setTextColor(this.resources.getColor(R.color.green))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
            binding.detailChangeTextView.text =
                "+ ${String.format("%.2f", data.quotes[0].percentChange24h)} %"
        } else {
            binding.detailChangeTextView.setTextColor(this.resources.getColor(R.color.red))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
            binding.detailChangeTextView.text =
                " ${String.format("%.2f", data.quotes[0].percentChange24h)} %"
        }


    }
}