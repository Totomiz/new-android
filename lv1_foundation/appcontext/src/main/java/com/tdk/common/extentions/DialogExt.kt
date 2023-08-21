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

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

fun DialogFragment.safeShow(fragmentManager: FragmentManager, tag: String? = null) {
    if (fragmentManager.isStateSaved) {
        return
    }
    if (this.isVisible) {
        return
    }
    this.show(fragmentManager, tag)
}

fun DialogFragment.safeDismiss() {
    if (this.isStateSaved) {
        return
    }
    this.dismiss()
}

fun DialogFragment.safeShow(activity: FragmentActivity, tag: String? = null) {
    this.safeShow(activity.supportFragmentManager, tag)
}

/**
 * 根据tag控制展示单一dialog
 * @param fm FragmentManager
 * @param tag String
 * @param t T
 * @return T
 */
inline fun <reified T : DialogFragment> showSingleDialog(
    fm: FragmentManager,
    tag: String,
    t: T
): T {
    val findFragmentByTag = fm.findFragmentByTag(tag)
    if (findFragmentByTag != null) {
        return findFragmentByTag as T
    } else {
        t.safeShow(fm, tag)
        return t
    }
}

fun getFindDialogFragment(fragmentManager: FragmentManager, tag: String? = null): Fragment? {
    return fragmentManager.findFragmentByTag(tag)
}