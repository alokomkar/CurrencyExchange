package com.alokomkar.remote.base

import com.google.gson.annotations.SerializedName

open class BaseParser(@SerializedName("success") var success: Boolean = false)