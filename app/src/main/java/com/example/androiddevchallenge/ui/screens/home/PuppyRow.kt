/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.request.RequestOptions
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.screens.model.Puppy
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun PuppyRow(puppy: Puppy, onPuppySelected: (Long, String) -> Unit) {
    Row(
        Modifier
            .height(56.dp)
            .clickable {
                onPuppySelected(puppy.id, puppy.name)
            }
    ) {
        Box(modifier = Modifier.weight(1f)) {
            GlideImage(
                modifier = Modifier.size(56.dp, 56.dp),
                data = puppy.photoUrl,
                contentDescription = "",
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_puppy_placeholder_purple_56),
                        contentDescription = ""
                    )
                },
                requestBuilder = {
                    val options = RequestOptions()
                    options.circleCrop()
                    apply(options)
                }
            )
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = puppy.name, color = MaterialTheme.colors.secondary)
        }
    }
}
