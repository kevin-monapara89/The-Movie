package com.kevin.themovie.Activity

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.kevin.themovie.Adapter.FragmentAdapter
import com.kevin.themovie.Fragment.NowPlayingMovie
import com.kevin.themovie.Fragment.PopularMovie
import com.kevin.themovie.Fragment.TopRatedMovie
import com.kevin.themovie.Fragment.UpcominMovie
import com.kevin.themovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var item = arrayOf("Now Playing", "Popular", "Top Rated", "Upcoming")

    var fragments = arrayOf(NowPlayingMovie(), PopularMovie(), TopRatedMovie(), UpcominMovie())

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.adapter = FragmentAdapter(supportFragmentManager, fragments, item)
        binding.tabitem.setupWithViewPager(binding.viewpager)

        initview()
    }

    private fun initview() {

        var manager: ConnectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        var networkInfo = manager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isAvailable) {
            binding.maincontent.isVisible = true
            binding.nointernet.isVisible = false
        } else {
            binding.nointernet.isVisible = true
            binding.maincontent.isVisible = false
        }

    }
}