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

package com.tz.mad.ui.kosample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tdk.basic.log.TLog
import com.tz.mad.ui.theme.NewandroidTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class KoSampleActivity : ComponentActivity() {

    val vm: KoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(vm.sayHello("Android"))
                }
            }
            TLog.i("aaa", "onCreate: ")
            TLog.i("onCreate: ")
            TLog.i("onCreate: ", this)
            TLog.i(this)
            TLog.e("onCreate: ")
            TLog.d("onCreate: ")
            TLog.w("onCreate: ")
            TLog.e("onCreate: ", Exception("aaa"))
            TLog.e("onCreate: ", "AAAA", Exception("CCCC"), "GGGGGG")

        }
        GlobalScope.launch(Dispatchers.IO) {
            for (index in 1 until 10) {
                //同步执行
                launch {
                    TLog.d("launch$index", "启动一个协程")
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewandroidTheme {
        Greeting("Android")
    }
}