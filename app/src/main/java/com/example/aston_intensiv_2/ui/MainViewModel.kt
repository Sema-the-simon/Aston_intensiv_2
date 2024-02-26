package com.example.aston_intensiv_2.ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_2.calculateSliceIndex
import com.example.aston_intensiv_2.getNameFromData
import com.example.aston_intensiv_2.randomRotation
import com.example.aston_intensiv_2.randomUri
import com.example.aston_intensiv_2.ui.customView.SpinningWheelView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val ANIMATION_DURATION = 4_000L

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val spinAnimator = ValueAnimator.ofFloat()
    private val animatorSet = AnimatorSet()

    fun setupSpinAnimator(animatedView: SpinningWheelView) {
        spinAnimator.apply {
            duration = ANIMATION_DURATION
            interpolator = AccelerateDecelerateInterpolator()

            doOnStart {
                _uiState.update {
                    it.copy(
                        isWheelSpinning = true
                    )
                }
            }
            addUpdateListener { animator ->
                _uiState.update {
                    it.copy(
                        rotation = (animator.animatedValue as Float)
                    )
                }
                animatedView.requestLayout()
                animatedView.invalidate()
            }
            doOnEnd {
                val sliceIndex = calculateSliceIndex(uiState.value.rotation)
                if (sliceIndex % 2 == 0) {
                    loadText(sliceIndex)
                } else {
                    loadImg()
                }
                _uiState.update {
                    it.copy(
                        isWheelSpinning = false
                    )
                }
            }

        }
        animatorSet.play(spinAnimator)
    }

    fun spin() {
        val rotation = uiState.value.rotation
        val resultAngle = randomRotation()
        spinAnimator.setFloatValues(rotation, resultAngle)
        animatorSet.start()
    }

    fun reset(animatedView: SpinningWheelView) {
        _uiState.update {
            it.copy(
                text = "",
                imgUri = ""
            )
        }
        animatedView.invalidate()
    }

    private fun loadText(index: Int) {
        _uiState.update {
            it.copy(
                text = getNameFromData(index),
                imgUri = ""
            )
        }
    }

    private fun loadImg() {
        _uiState.update {
            it.copy(
                text = "",
                imgUri = randomUri()
            )
        }
    }

}

data class MainUiState(
    val isWheelSpinning: Boolean = false,
    val rotation: Float = 0f,
    val text: String = "",
    val imgUri: String = "",
)
