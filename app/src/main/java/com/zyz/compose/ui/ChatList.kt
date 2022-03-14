package com.zyz.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zyz.compose.data.Chat
import com.zyz.compose.ui.theme.WeComposeTheme

@Composable
fun ChatList(chats: List<Chat>) {
//  Box(Modifier.fillMaxSize())
    Box(
        Modifier
            .background(WeComposeTheme.colors.background)
            .fillMaxSize()
    ){
        //在 Compose 中使用 LazyColumn 来代替 ListView 和 RecyclerView 实现列表
        LazyColumn(Modifier.background(WeComposeTheme.colors.listItem)){
            //使用 items() 方法能直接遍历 List,但 items() 方法只能在 LazyColumn{} 代码块中使用
            //为了拿到 index, 则使用 itemsIndexed() 方法
            itemsIndexed(chats){index, chat ->
                ChatListItem(chat)
                //如果不是最后一个才显示分割线
                if(index < chats.lastIndex){ //chats.lastIndex = chats.size - 1
                    Divider(
                        startIndent = 68.dp, //到左边的距离
                        color = WeComposeTheme.colors.chatListDivider,
                        thickness = 0.8f.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatListItem(chat: Chat) {
    Row(
        Modifier.fillMaxWidth()
    ) {
//          Image() 用来显示图片, 而 Icon() 用来显示纯色图标, Modifier.size() 是尺寸, Modifier.size().clip() 用来设置圆角
        Image(
            painterResource(chat.friend.avatar),
            chat.friend.name,
            Modifier
                .padding(8.dp)
                .size(48.dp)
                .unread(
                    !chat.msgs.last().read,
                    WeComposeTheme.colors.badge
                )        //聊天记录中的最新一条是否是未读的, 如果是未读那么就显示
                .clip(RoundedCornerShape(4.dp))
        )
//              通过设置 Column 的 weight = 1 来保证 Text 在最后边
        Column(
            Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(chat.friend.name, fontSize = 17.sp, color = WeComposeTheme.colors.textPrimary)
            Text(
                chat.msgs.last().text,
                fontSize = 14.sp,
                color = WeComposeTheme.colors.textSecondary
            )
        }
        Text(
            text = chat.msgs.last().time,
            modifier = Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp),
            fontSize = 11.sp,
            color = WeComposeTheme.colors.textSecondary
        )
    }
}

fun Modifier.unread(show: Boolean, color: Color): Modifier = this.drawWithContent {
    drawContent()
    if(show){
        //因为 badge 是从 WeComposeTheme.colors 中取出来的, 而 WeComposeTheme.colors 的 get() 方法 :
        //      get() = LocalWeComposeColors.current
        // LocalWeComposeColors 是一个 CompositionLocal 类型的变量, 使用 LocalWeComposeColors.current 取值必须在一个 Composable 函数中 (即被 @Composable 注解标注的函数),
        // 所以需要使用外部传的 color, 而不是直接使用 WeComposeTheme.colors.badge
        // drawCircle(WeComposeTheme.colors.badge, 5.dp.toPx(), Offset(size.width - 1.dp.toPx(), 1.dp.toPx()))
        drawCircle(color, 5.dp.toPx(), Offset(size.width - 1.dp.toPx(), 1.dp.toPx()))
    }
}