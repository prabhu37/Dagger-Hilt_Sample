package com.prabhu.socialmediatest.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.prabhu.socialmediatest.R
import com.prabhu.socialmediatest.databinding.ActivityMainBinding
import com.prabhu.socialmediatest.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUI()
    }


    private fun setUI() {
        Utils.verifyStoragePermissions(this)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

}