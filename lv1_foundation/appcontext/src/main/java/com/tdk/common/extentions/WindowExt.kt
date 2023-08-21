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

import android.annotation.TargetApi
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager

var Window.isSystemBarsTranslucentCompat: Boolean
    get() {
        throw UnsupportedOperationException("set value only")
    }
    set(value) {
        if (Build.VERSION.SDK_INT >= 30) {
            setDecorFitsSystemWindows(!value)
        } else {
            decorView.systemUiVisibility =
                if (value) {
                    decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                } else {
                    decorView.systemUiVisibility and
                        (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION).inv()
                }
        }

        if (Build.VERSION.SDK_INT >= 28) {
            if (value) {
                attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            } else {
                attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
            }
        }
    }

var Window.isLightStatusBarsCompat: Boolean
    get() {
        throw UnsupportedOperationException("set value only")
    }
    @TargetApi(23)
    set(value) {
        if (value) {
            if (Build.VERSION.SDK_INT >= 30) {
                decorView.windowInsetsController?.apply {
                    setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            } else {
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } else {
            if (Build.VERSION.SDK_INT >= 30) {
                decorView.windowInsetsController?.apply {
                    setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            } else {
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

var Window.isLightNavigationBarCompat: Boolean
    get() {
        throw UnsupportedOperationException("set value only")
    }
    @TargetApi(27)
    set(value) {
        if (value) {
            if (Build.VERSION.SDK_INT >= 30) {
                decorView.windowInsetsController?.apply {
                    setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }
            } else {
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        } else {
            if (Build.VERSION.SDK_INT >= 30) {
                decorView.windowInsetsController?.apply {
                    setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }
            } else {
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
        }
    }

var Window.isAllowForceDarkCompat: Boolean
    get() {
        return if (Build.VERSION.SDK_INT >= 29) {
            decorView.isForceDarkAllowed
        } else {
            false
        }
    }
    set(value) {
        if (Build.VERSION.SDK_INT >= 29) {
            decorView.isForceDarkAllowed = value
        }
    }