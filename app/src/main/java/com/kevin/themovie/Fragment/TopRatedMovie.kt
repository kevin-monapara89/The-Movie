package com.kevin.themovie.Fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.themovie.Adapter.MovieAdapter
import com.kevin.themovie.Api.ApiClient
import com.kevin.themovie.Api.ApiInterface
import com.kevin.themovie.Model.MovieModel
import com.kevin.themovie.Model.ResultsItem
import com.kevin.themovie.databinding.FragmentTopRatedMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedMovie : Fragment() {

    lateinit var binding: FragmentTopRatedMovieBinding
    var page = 1
    var adapter = MovieAdapter()
    var movielist = ArrayList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopRatedMovieBinding.inflate(layoutInflater)

        binding.nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                TopratedrmovieAPI(page)
            }
        })

        TopratedrmovieAPI(page)

        return binding.root
    }

    private fun TopratedrmovieAPI(page: Int) {
        var api = ApiClient.getApiClient().create(ApiInterface::class.java)
        api.gettoprated(this.page).enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.isSuccessful) {
                    var popularmovie = response.body()?.results
                    movielist.addAll(popularmovie as ArrayList<ResultsItem>)

                    adapter.setMovies(movielist)
                    binding.rcvtoprated.layoutManager = LinearLayoutManager(context)
                    binding.rcvtoprated.adapter = adapter
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}