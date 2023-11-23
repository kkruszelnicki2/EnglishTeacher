package com.example.learnenglish

import android.view.View

interface LevelClickListener {
    fun onLevelClick(view: View,levelNumber: Int,levelName: String)
}