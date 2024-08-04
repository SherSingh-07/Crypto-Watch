package com.example.cryptowatch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.GONE
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.VISIBLE
import com.example.cryptowatch.adapter.TopLossGainPagerAdapter
import com.example.cryptowatch.adapter.TopMarketAdapter
import com.example.cryptowatch.apis.ApiInterface
import com.example.cryptowatch.apis.ApiUtilities
import com.example.cryptowatch.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class fragment_home : Fragment() {

      private lateinit var binding: FragmentHomeBinding
      private lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        binding = FragmentHomeBinding.inflate(layoutInflater)



        setTabLayout()
        getTopCurrencyList()



        return binding.root

    }

    private fun setTabLayout() {
        val adapter= TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter= adapter

        binding.contentViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){  ////ackjsadjkjbadbadakjdd

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0){
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                }else{

                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

       TabLayoutMediator(binding.tabLayout,binding.contentViewPager){
           tab,position ->
           var title = if (position==0){
               "Top Gainers"
           }else{
               "Top Losers"
           }
           tab.text= title
       }.attach()

    }

    private fun getTopCurrencyList() {

        lifecycleScope.launch(Dispatchers.IO){
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            val dataList = res.body()!!.data.cryptoCurrencyList.subList(0,10)


            withContext(Dispatchers.Main){
                binding.topCurrencyRecyclerView.adapter= TopMarketAdapter(requireContext(),dataList)
            }

            Log.d("SHER", "getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
