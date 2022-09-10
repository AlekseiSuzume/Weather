package com.suzume.weather.presentation

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.suzume.weather.App
import com.suzume.weather.R
import com.suzume.weather.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).companion
    }

    private lateinit var defaultCity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.navigationBarColor = resources.getColor(R.color.transparent, theme)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setupSearchView()
        setupSwipe()
        observeViewModel()
    }

    private fun observeViewModel() {
        setupView()
        ifErrorConnection()
        ifUnknownCity()
        isLoading()
    }

    private fun setupSwipe() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.loadWeather(defaultCity)
            binding.swipeLayout.isRefreshing = false
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query == null || query.isEmpty()) {
                    false
                } else {
                    viewModel.loadWeather(query.trim())
                    binding.searchView.onActionViewCollapsed()
                    true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private fun setupView() {
        viewModel.getWeather().observe(this) {
            if (it == null) {
                loadDefaultCity()
            } else {
                Log.d("MyTest:MainActivity", it.toString())
                defaultCity = it.name
                with(binding) {
                    tvCity.text = it.name
                    tvTemp.text = it.temp.toString()
                    tvHumidity.text = it.humidity.toString()
                    tvPressure.text = it.pressure.toString()
                    tvWind.text = it.windSpeed.toString()
                    tvDescription.text = it.description
                    tvTimeUpdate.text = it.lastTimeUpdate
                }
            }
        }
    }

    private fun loadDefaultCity() {
        defaultCity = resources.getString(R.string.default_city)
        viewModel.loadWeather(defaultCity)
    }

    private fun ifErrorConnection() {
        viewModel.errorConnection.observe(this) {
            if (it) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun ifUnknownCity() {
        viewModel.errorUnknownCity.observe(this) {
            if (it) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_unknown_city),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isLoading() {
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            binding.searchView.onActionViewCollapsed()
        }
        return super.onTouchEvent(event)
    }

}