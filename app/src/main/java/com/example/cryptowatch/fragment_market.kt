package com.example.cryptowatch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptowatch.adapter.MarketAdapter
import com.example.cryptowatch.apis.ApiInterface
import com.example.cryptowatch.apis.ApiUtilities
import com.example.cryptowatch.databinding.FragmentMarketBinding
import com.example.cryptowatch.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class fragment_market : Fragment() {

      private lateinit var binding: FragmentMarketBinding
      private lateinit var list: List<CryptoCurrency>
      private lateinit var adapter: MarketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding= FragmentMarketBinding.inflate(layoutInflater)

        list = listOf()

        adapter= MarketAdapter(requireContext(),list)
        binding.currencyRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO){
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if (res.body()!=null){

                withContext(Dispatchers.Main){
                    list = res.body()!!.data.cryptoCurrencyList

                    adapter.updateData(list)

                }
            }
        }

        searchCoin()


        return binding.root
    }
    lateinit var searchText : String

    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                  searchText = s.toString().toLowerCase()

                updateRecyclerView()
            }


        })


    }

    private fun updateRecyclerView() {
        val data = ArrayList<CryptoCurrency>()

        for (item in list){
            val coinName = item.name.lowercase(Locale.getDefault())
            val coinSymbols = item.symbol.lowercase(Locale.getDefault())

          if (coinName.contains(searchText) || coinSymbols.contains(searchText)){
                data.add(item)
     }

        }

        adapter.updateData(data)

    }


}
