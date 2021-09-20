package com.sewar.sary.ui

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.sewar.sary.MyApplication
import com.sewar.sary.injection.component.ActivityComponent
import com.sewar.sary.injection.component.DaggerActivityComponent

@SuppressLint("Registered")
open class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {
    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent
            .factory()
            .create((applicationContext as MyApplication).applicationComponent)
    }
}
