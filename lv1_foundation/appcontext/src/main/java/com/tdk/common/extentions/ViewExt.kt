/*
 * Copyright 2023  T Open Source Project . All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   Created by T on 2023/8/22.
 */

package com.tdk.common.extentions

import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat

val View.inflater: LayoutInflater
    get() = LayoutInflater.from(this.context)

var TextView.textAppearance: Int
    get() = throw UnsupportedOperationException("set value only")
    set(@StyleRes value) {
        if (Build.VERSION.SDK_INT >= 23) {
            setTextAppearance(value)
        } else {
            setTextAppearance(context, value)
        }
    }

fun TextView.applyUnderLine() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.applyStrikeLine() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.applyTypeFace(resId: Int) {
    ResourcesCompat.getFont(this.context, resId, object : ResourcesCompat.FontCallback() {
        override fun onFontRetrieved(typeface: Typeface) {
            this@applyTypeFace.typeface = typeface
        }

        override fun onFontRetrievalFailed(reason: Int) {
        }
    }, null)
}

fun TextView.applyStrikeThroughLine() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * 原基础上额外padding
 * @receiver View
 * @param l Int
 * @param t Int
 * @param r Int
 * @param b Int
 */
fun View.applyExtraPadding(l: Int = 0, t: Int = 0, r: Int = 0, b: Int = 0) {
    this.setPadding(
        this.paddingLeft + l,
        this.paddingTop + t,
        this.paddingRight + r,
        this.paddingBottom + b
    )
}

/**
 * 设置边角区
 * @receiver View
 * @param cornerRadius Float
 */
fun View.applyOutlineProvider(cornerRadius: Float = 8f) {
    this.clipToOutline = true
    this.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            view?.apply {
                outline?.setRoundRect(0, 0, width, height, cornerRadius)
            }
        }
    }
}