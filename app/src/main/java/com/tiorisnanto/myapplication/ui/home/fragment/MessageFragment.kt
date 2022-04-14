package com.tiorisnanto.myapplication.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.databinding.FragmentMessageBinding
import com.tiorisnanto.myapplication.ui.home.fragment.message.adapter.NewsAdapter
import com.tiorisnanto.myapplication.ui.home.fragment.message.adapter.SliderAdapter
import com.tiorisnanto.myapplication.ui.home.fragment.message.api.ApiConfig
import com.tiorisnanto.myapplication.ui.home.fragment.message.model.ResponseNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageFragment : Fragment() {

    private lateinit var binding : FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_message, container, false)

        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recvNews = activity?.findViewById<RecyclerView>(R.id.rvNews)
        val recvNewsHorizontal = activity?.findViewById<RecyclerView>(R.id.carouselRecyclerView)

        ApiConfig.getApiService().getNews().enqueue(object : Callback<ResponseNews>{
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                if (response.isSuccessful){
                    val responseNews = response.body()
                    val dataNews = responseNews?.articles
                    val newsAdapter = NewsAdapter(dataNews)
                    recvNews?.apply {
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        newsAdapter.notifyDataSetChanged()
                        adapter = newsAdapter

                    }
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

        ApiConfig.getApiService().getNews().enqueue(object : Callback<ResponseNews>{
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                if (response.isSuccessful){
                    val responseNews = response.body()
                    val dataNews = responseNews?.articles
                    val newsAdapter = SliderAdapter(dataNews)
                    recvNewsHorizontal?.apply {
                        val lmb = LinearLayoutManager(context)
                        lmb.orientation = LinearLayoutManager.HORIZONTAL
                        layoutManager = lmb
                        setHasFixedSize(true)
                        newsAdapter.notifyDataSetChanged()
                        adapter = newsAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }
}