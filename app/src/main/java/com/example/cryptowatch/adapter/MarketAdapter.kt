package com.example.cryptowatch.adapter

import android.content.Intent
import android.annotation.SuppressLint
import android.content.Context
//import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.cryptowatch.DetailsActivity
import com.example.cryptowatch.R
import com.example.cryptowatch.databinding.FragmentCurrencyItemLayoutBinding
import com.example.cryptowatch.models.CryptoCurrency
import com.example.cryptowatch.watchlist_activity

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>): Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var fragmentManager : FragmentManager

       inner class MarketViewHolder(view:View):RecyclerView.ViewHolder(view){
           var binding = FragmentCurrencyItemLayoutBinding.bind(view)
       }

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
          return MarketViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_currency_item_layout,parent,false))
       }

       fun updateData(dataItem : List<CryptoCurrency>){
           list= dataItem
           notifyDataSetChanged()
       }

       override fun getItemCount(): Int {
           return list.size
       }

       @SuppressLint("CheckResult")
       override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
           val item = list[position]
           holder.binding.currencyNameTextView.text = item.name
           holder.binding.currencySymbolTextView.text = item.symbol
           Log.d("check", "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png")
           Glide.with(context).load(
               "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
           ).thumbnail(Glide.with(context).load(R.drawable.spinner))
               .into(holder.binding.currencyImageView)

           Glide.with(context).load(
               "https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png"
           ).thumbnail(Glide.with(context).load(R.drawable.spinner))
               .into(holder.binding.currencyChartImageView)

           holder.binding.currencyPriceTextView.text = String.format("%.4f", item.quotes[0].price)


           if (item.quotes[0].percentChange24h > 0) {

               holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
               holder.binding.currencyChangeTextView.text =
                   "+ ${String.format("%.2f", item.quotes[0].percentChange24h)} %"
           } else {
               holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
               holder.binding.currencyChangeTextView.text =
                   " ${String.format("%.2f", item.quotes[0].percentChange24h)} %"

           }



           holder.itemView.setOnClickListener {
               val intent = Intent(context, DetailsActivity::class.java)
               intent.putExtra("data", item)
               context.startActivity(intent)
           }
//               findNavController(it).navigate(
//                   fragment_marketDirections.actionFragmentMarketToFragmentDetails(item)
////                   fragment_homeDirections.actionFragmentHomeToFragmentDetails(item)
//
//               )


//           holder.itemView.setOnClickListener {
//               val intent = Intent(context, watchlist_activity::class.java)
//               intent.putExtra("watchlist", item)
//               context.startActivity(intent)
//
//
//           }


       }
}


