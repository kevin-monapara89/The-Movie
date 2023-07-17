package com.kevin.themovie

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.kevin.themovie.databinding.ActivityMainBinding
import com.kevin.themovie.databinding.FragmentUpcominMovieBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var item = arrayOf("Upcoming","Popular")
//    "Now Playing",,"Top Rated",
    var fragments = arrayOf(UpcominMovie(), PopularMovie())

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

        var manager: ConnectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        var networkInfo = manager.activeNetworkInfo

        if(networkInfo != null && networkInfo.isAvailable) {
            binding.maincontent.isVisible = true
            binding.nointernet.isVisible = false
        } else {
            binding.nointernet.isVisible = true
            binding.maincontent.isVisible = false
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragepage, fragment).commit()
    }
}