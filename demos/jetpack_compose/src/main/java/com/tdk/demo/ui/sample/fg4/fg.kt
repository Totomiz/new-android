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

package com.tdk.demo.ui.sample.fg4

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdk.demo.jetpack_compose.R

//
// Added by T on 2023/9/1.
//
data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    // 添加padding
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "A profile picture",
            modifier = Modifier
                .clickable {
                    Log.d("aaaa", "addd")
                }
                .size(40.dp)
                .clip(CircleShape),

            )
        //添加水平间接
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = msg.author)
            //添加竖直间距
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body)
        }

    }
}

@Preview(device = "id:Nexus 5", showSystemUi = true, showBackground = true)
@Composable
fun PreviewMessageCard() {
    MessageCard(Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!"))
}