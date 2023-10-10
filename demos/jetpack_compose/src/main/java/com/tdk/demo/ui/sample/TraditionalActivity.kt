/*
 * Copyright 2023-Now T Open Source Project .
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
 */

package com.tdk.demo.ui.sample

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tdk.Vert
import com.tdk.app.appCtx
import com.tdk.basic.log.TLog
import com.tdk.basic.log.configure
import com.tdk.demo.jetpack_compose.databinding.TraditionalActivityBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class TraditionalActivity : AppCompatActivity() {
    lateinit var binding: TraditionalActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TraditionalActivityBinding.inflate(layoutInflater).also {
            binding = it
            setContentView(binding.root)
        }
        TLog.configure {
            it
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            val vert = Vert().also {
                it.setUp()
                val path = appCtx.getExternalFilesDir("")

                val srcf = "src_a.txt"
                val dstf = "dst_a.txt"
                val addFile = File(path, srcf)
                if (!addFile.exists()) {
                    addFile.createNewFile()
                    addFile.writeText(it.testBlockString)
                }
                it.test(appCtx, srcf, dstf)

            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

