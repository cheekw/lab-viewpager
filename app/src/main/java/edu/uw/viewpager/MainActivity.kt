package edu.uw.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity(), MovieListFragment.OnMovieSelectedListener,
    SearchFragment.OnSearchListener {
    private var searchFragment: SearchFragment? = null
    private var listFragment: MovieListFragment? = null
    private var detailFragment: DetailFragment? = null
    private var pager: ViewPager? = null
    private var pagerAdapter: PagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchFragment = SearchFragment.newInstance()
        pager = findViewById<ViewPager>(R.id.pager)
        pagerAdapter = MoviePagerAdapter(supportFragmentManager)
        pager!!.adapter = pagerAdapter
    }

    override fun onSearchSubmitted(searchTerm: String) {
        listFragment = MovieListFragment.newInstance(searchTerm)
        pagerAdapter!!.notifyDataSetChanged()
        pager!!.currentItem = 1
    }

    override fun onMovieSelected(movie: Movie) {
        Log.v(TAG, "Detail for $movie")
        detailFragment = DetailFragment.newInstance(movie)
        pagerAdapter!!.notifyDataSetChanged()
        pager!!.currentItem = 2 //hard-code the shift
    }

    private inner class MoviePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return if (listFragment == null) {
                1
            } else if (detailFragment == null) {
                2
            } else {
                3
            }
        }

        override fun getItem(position: Int): Fragment? {
            if (position == 0) return searchFragment
            if (position == 1) return listFragment
            return if (position == 2) detailFragment else null
        }

        override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
        }
    }

    override fun onBackPressed() {
        if (pager!!.currentItem == 0) {
            super.onBackPressed()
        } else {
            pager!!.currentItem = pager!!.currentItem - 1
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
