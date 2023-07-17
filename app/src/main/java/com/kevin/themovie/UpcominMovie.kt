package com.kevin.themovie

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.themovie.databinding.FragmentUpcominMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcominMovie : Fragment() {

    var page = 1
    lateinit var binding: FragmentUpcominMovieBinding
    var adapter = MovieAdapter()
    var movielist = ArrayList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUpcominMovieBinding.inflate(layoutInflater)

        binding.nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page ++
                UpcomingMovieAPI(page)
            }
        })
        UpcomingMovieAPI(page)
        return binding.root
    }

    private fun UpcomingMovieAPI(page: Int) {
        var api = ApiClient.getApiClient().create(ApiInterface::class.java)
        api.getupcoming(page).enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.isSuccessful) {
                    var upcomingmovie = response.body()?.results
                    movielist.addAll(upcomingmovie as ArrayList<ResultsItem>)

                    adapter.setMovies(movielist)
                    binding.rcvupcoming.layoutManager = LinearLayoutManager(context)
                    binding.rcvupcoming.adapter = adapter
                }
            }
            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", )
            }
        })

    }
}