package com.example.cryptowatch.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptowatch.DetailsActivity
import com.example.cryptowatch.R
import com.example.cryptowatch.databinding.FragmentTopCurrencyLayoutBinding
import com.example.cryptowatch.models.CryptoCurrency
import com.example.cryptowatch.top_currency_layout
import com.example.cryptowatch.watchlist_activity


class TopMarketAdapter (var context: Context,val list: List<CryptoCurrency>): RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>(){


        inner class TopMarketViewHolder(view : View) : RecyclerView.ViewHolder(view){
                var binding = FragmentTopCurrencyLayoutBinding.bind(view)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
                return TopMarketViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_top_currency_layout,parent,false))
        }

        override fun getItemCount(): Int {
                return list.size
        }

        override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
                val item = list[position]

                holder.binding.topCurrencyNameTextView.text=item.name

                Glide.with(context).load(
                        "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
                ).thumbnail(Glide.with(context).load(R.drawable.spinner))
                        .into(holder.binding.topCurrencyImageView)

                if(item.quotes[0].percentChange24h> 0){

                        holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
                        holder.binding.topCurrencyChangeTextView.text= "+ ${String.format("%.2f",item.quotes[0].percentChange24h)} %"
                }else{
                        holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
                        holder.binding.topCurrencyChangeTextView.text= " ${String.format("%.2f",item.quotes[0].percentChange24h)} %"
                }

                holder.itemView.setOnClickListener{
                        val intent = Intent(context, DetailsActivity::class.java)
                        intent.putExtra("data", item)
                        context.startActivity(intent)
                }

//                holder.itemView.setOnClickListener {
//                        val intent = Intent(context, watchlist_activity::class.java)
//                        intent.putExtra("watchlist", item)
//                        context.startActivity(intent)
//
//
//                }
        }
}