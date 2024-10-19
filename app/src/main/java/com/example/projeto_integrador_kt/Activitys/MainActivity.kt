package com.example.projeto_integrador_kt.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.projeto_integrador_kt.Activitys.Adapter.BrandAdapter
import com.example.projeto_integrador_kt.Activitys.Adapter.PopularAdapter
import com.example.projeto_integrador_kt.Activitys.Adapter.SliderAdapter
import com.example.projeto_integrador_kt.Activitys.Model.SliderModel
import com.example.projeto_integrador_kt.Activitys.ViewModel.MainViewModel
import com.example.projeto_integrador_kt.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private var viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun initBanner() {
        binding.progessBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items->
            banners(items)
            binding.progessBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images:List<SliderModel>){
        binding.viewPageSlider.adapter = SliderAdapter(images, binding.viewPageSlider)
        binding.viewPageSlider.clipToPadding = false
        binding.viewPageSlider.clipChildren = false
        binding.viewPageSlider.offscreenPageLimit = 3
        binding.viewPageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPageSlider.setPageTransformer(compositePageTransformer)

    }

    private fun initBrand(){
        binding.progressBarBrand.visibility =View.VISIBLE
        viewModel.brands.observe(this, Observer {
            binding.viewBrand.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter = BrandAdapter(it)
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrand()
    }

    private fun initPopular(){
        binding.progressBarPopular.visibility =View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.viewPopular.layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.viewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }
}