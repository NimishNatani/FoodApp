package com.foodapp.foodapp.sharedObjects

import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
object SharedObject {
    var sharedUser: Boolean? = null
}