package com.suzume.weather.presentation

import android.os.Bundle
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
import com.suzume.weather.domain.entity.Weather
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

    private val sharedPref by lazy {
        getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.navigationBarColor = resources.getColor(R.color.transparent, theme)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        defaultCity = sharedPref.getString(DEFAULT_CITY, DEFAULT_CITY_VALUE).toString()

        loadDefaultCity()
        setupSearchView()
        setupSwipe()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.observe(this) {
            binding.progress.visibility = View.GONE
            when (it) {
                is Progress -> binding.progress.visibility = View.VISIBLE
                is Error -> showErrorToast(it)
                is ResultValue -> {
                    if (it.weather != null) {
                        setupContent(it.weather)
                    }
                }
            }
        }
    }

    private fun showErrorToast(it: Error) {
        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
    }

    private fun setupContent(weather: Weather) {
        sharedPref.edit().putString(DEFAULT_CITY, weather.name).apply()
        defaultCity = weather.name
        with(binding) {
            tvCity.text = weather.name
            tvTemp.text = weather.temp.toString()
            tvHumidity.text = weather.humidity.toString()
            tvPressure.text = weather.pressure.toString()
            tvWind.text = weather.windSpeed.toString()
            tvDescription.text = weather.description
            tvTimeUpdate.text = weather.lastTimeUpdate
        }
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

    private fun loadDefaultCity() {
        viewModel.loadWeather(defaultCity)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            binding.searchView.onActionViewCollapsed()
        }
        return super.onTouchEvent(event)
    }

    companion object {
        private const val DEFAULT_CITY = "name"
        private const val DEFAULT_CITY_VALUE = "Moscow"
    }

}