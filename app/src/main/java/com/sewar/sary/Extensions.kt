package com.sewar.sary.extensions

import android.os.Looper

fun checkNotMainThread() {
    check(Looper.myLooper() != Looper.getMainLooper())
    { "Cannot be called on the main thread" }
}
