package com.zyz.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.zyz.compose.ui.ChatList
import com.zyz.compose.ui.WeBottomBar
import com.zyz.compose.ui.theme.*

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeComposeTheme(WeComposeTheme.Theme.Dark) {
                Column {
                    val viewModel: WeViewModel = viewModel()
                    HorizontalPager(count = 4) { page ->
                        when (page) {
                            0 -> {
                                ChatList(viewModel.chats) //使用 Box 先占住位置
                            }
                            1 -> {
                                Box(Modifier.fillMaxSize())
                            }
                            2 -> {
                                Box(Modifier.fillMaxSize())
                            }
                            3 -> {
                                Box(Modifier.fillMaxSize())
                            }
                        }
                    }
                    WeBottomBar(viewModel.selectedTabIndex) {
                        viewModel.selectedTabIndex = it
                    }
                }
            }
        }
    }
}
