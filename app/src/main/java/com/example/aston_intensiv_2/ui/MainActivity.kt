package com.example.aston_intensiv_2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.example.aston_intensiv_2.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }
        setupAnimator()
        setListeners()
    }

    private fun setupAnimator() {
        viewModel.setupSpinAnimator(binding.spinningWheel)
    }

    private fun setListeners() {
        binding.apply {
            spinBtn.setOnClickListener {
                viewModel.spin()
            }
            resetBtn.setOnClickListener {
                viewModel.reset(spinningWheel)
            }
        }
    }

    private fun updateUi(state: MainUiState) {
        binding.spinningWheel.apply {
            rotationAngle = state.rotation
            isWheelSpinning = state.isWheelSpinning
            text = state.text
        }


        binding.imgView.apply{
            load(state.imgUri) {
                crossfade(true)
                diskCachePolicy(CachePolicy.DISABLED)
                memoryCachePolicy(CachePolicy.DISABLED)
            }
        }
    }
}