package com.kevin.themovie

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.themovie.databinding.FragmentPopularMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMovie : Fragment() {

    lateinit var binding: FragmentPopularMovieBinding
    var page = 1
    var adapter = MovieAdapter()
    var movielist = ArrayList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPopularMovieBinding.inflate(layoutInflater)

        binding.nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page ++
                popularmovieAPI(page)
            }
        })

        popularmovieAPI(page)
    return binding.root
    }

    private fun popularmovieAPI(page: Int) {
        var api = ApiClient.getApiClient().create(ApiInterface::class.java)
        api.getpopular(this.page).enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.isSuccessful) {
                    var popularmovie = response.body()?.results
                    movielist.addAll(popularmovie as ArrayList<ResultsItem>)

                    adapter.setMovies(movielist)
                    binding.rcvpopular.layoutManager = LinearLayoutManager(context)
                    binding.rcvpopular.adapter = adapter
                }
            }
            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}", )
            }
        })
    }

}