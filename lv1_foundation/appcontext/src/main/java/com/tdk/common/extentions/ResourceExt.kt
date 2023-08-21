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

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.tdk.app.appCtx
import com.tdk.app.appRes
import java.util.Locale

fun getAppString(@StringRes resId: Int, vararg formatArgs: Any): String =
    appCtx.getString(resId, *formatArgs)

fun getAppInteger(@IntegerRes idRes: Int) =
    appRes.getInteger(idRes)

fun getAppString(@StringRes resId: Int): String =
    appCtx.getString(resId)

fun getAppDrawable(@DrawableRes drawableRes: Int) =
    AppCompatResources.getDrawable(
        appCtx,
        drawableRes
    )

val Configuration.preferredLocale: Locale
    get() {
        return if (Build.VERSION.SDK_INT >= 24) {
            locales[0]
        } else {
            locale
        }
    }

fun Context.getColorCompat(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

fun Drawable.foreground(): Drawable {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
        this is AdaptiveIconDrawable && this.background == null
    ) {
        return this.foreground
    }
    return this
}

