package com.kevin.themovie.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kevin.themovie.Api.ApiClient
import com.kevin.themovie.Model.ResultsItem
import com.kevin.themovie.databinding.MovielistBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.UpcomingHolder>(){

    lateinit var context: Context
    lateinit var movielist: List<ResultsItem?>

    class UpcomingHolder(itemView: MovielistBinding) : ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingHolder {
        context = parent.context
        var binding = MovielistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UpcomingHolder(binding)
    }

    override fun getItemCount(): Int {
       return movielist.size
    }

    override fun onBindViewHolder(holder: UpcomingHolder, position: Int) {
        holder.binding.apply {
            movielist.get(position)?.apply {
                txtmoviename.text = originalTitle
                txtdescription.text = overview
                txtrateing.text = voteAverage.toString()
                txtpopularity.text = popularity.toString()
                Glide.with(context).load(ApiClient.posterUrl +posterPath).into(imgposter)
            }
        }

    }

    fun setMovies(movie: List<ResultsItem?>?) {
        this.movielist = movie as List<ResultsItem?>
    }
}