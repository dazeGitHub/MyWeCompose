package com.zyz.compose.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zyz.compose.R
import com.zyz.compose.WeViewModel
import com.zyz.compose.ui.theme.WeComposeTheme

//selectedIndex : 选中的是第几个
@Composable
fun WeBottomBar(selectedIndex: Int, onSelectedChangedListener: (Int) -> Unit) {
//    val viewModel: WeViewModel = viewModel()
    Row(Modifier.background(WeComposeTheme.colors.bottomBar)) {
        //每个选项卡中又是由两个纵向元素 (图标 和 文字) 组成的, 所以要写四个 Column 做为选项卡
        TabItem(
            if(selectedIndex == 0) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined,
            "聊天",
            if(selectedIndex == 0) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier.weight(1f).clickable {
//              viewModel.selectedTabIndex = 0
                onSelectedChangedListener(0)
            }
        )
        TabItem(
            if(selectedIndex == 1) R.drawable.ic_contacts_filled else R.drawable.ic_contacts_outlined,
            "通讯录",
            if(selectedIndex == 1) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier.weight(1f).clickable {
                onSelectedChangedListener(1)
            }
        )
        TabItem(
            if(selectedIndex == 2) R.drawable.ic_discovery_filled else R.drawable.ic_discovery_outlined,
            "发现",
            if(selectedIndex == 2) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier.weight(1f).clickable {
                onSelectedChangedListener(2)
            }
        )
        TabItem(
            if(selectedIndex == 3) R.drawable.ic_me_filled else R.drawable.ic_me_outlined,
            "我",
            if(selectedIndex == 3) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier.weight(1f).clickable {
                onSelectedChangedListener(3)
            }
        )
    }
}

@Composable
private fun TabItem(@DrawableRes iconId: Int, title : String, tint : Color, modifier: Modifier = Modifier) {

    //这里只设置纵向的 padding, 因为横向的需要平分空间而不是使用 padding
    Column(
        modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //从本地取图片使用 painterResource() 函数,
        // 选项卡本身有两种状态, 选中是实心绿色, 则使用 R.drawable.ic_chat_filled; 未选中是空心黑色, 使用 R.drawable.ic_chat_outlined

        // contentDescription 是用于无障碍模式的
        // 在 Compose 中只有 padding 而不和以前一样有 margin 和 padding, 但是仍然不影响实现功能, 这里给 Column 加 padding 而不是给 Icon 加 padding
//      Icon(painterResource(id = iconId), contentDescription = title, modifier = Modifier.padding(8.dp))

        //Modifier.size(24.dp) 即设置的宽和高都是 24dp
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = title,
            modifier = Modifier.unread(true, WeComposeTheme.colors.badge).size(24.dp),
            tint = tint
        )

        //在传统的界面中是使用 setText()、setBackground() 来控制的, 但是在 Compose 中是使用函数参数来控制的, 例如 text
        Text(text = title, fontSize = 11.sp, color = tint)
    }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreview() {
    WeComposeTheme(WeComposeTheme.Theme.Light) {
        //在 @Composable 注解方法内使用 mutableStateOf() 必须使用 remember{} 函数来包住
        var selectedTab by remember{ mutableStateOf(0) }
        WeBottomBar(selectedIndex = selectedTab){selectedTab = it}
    }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreviewDark() {
    WeComposeTheme(WeComposeTheme.Theme.Dark) {
        var selectedTab by remember{ mutableStateOf(0) }
        WeBottomBar(selectedIndex = selectedTab){selectedTab = it}
    }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreviewNewYear() {
    WeComposeTheme(WeComposeTheme.Theme.NewYear) {
        var selectedTab by remember{ mutableStateOf(0) }
        WeBottomBar(selectedIndex = selectedTab){selectedTab = it}
    }
}

@Preview(showBackground = true)
@Composable
fun TabItemPreview(){
    //MaterialTheme 是一个单例对象, 包含了一系列的通用属性, 例如 App 的主色调、次色调、错误色调、不同色调下的文字颜色,
    //但是 MaterialTheme.colors 并不是 material 中的 COLORS 类型的对象, 而是 CompositionLocal 类型的对象,
    //CompositionLocal 对象内部包裹的才是真正的 COLORS 对象, 即它是 CompositionLocal<Colors> 类型的对象
    //只在某一段 @Composable 注解的函数内部可用的变量, 而出了这块作用域就自动失效了, 即相当于给函数用的局部变量

    //如果将代码包含在 MaterialTheme 里边, 例如上边的 TestJetpackComposeTheme {}, 那么就可以在代码中使用 MaterialTheme 提供的值
    //并且这些值可以自行配置, 例如将主色调配置为蓝色, 也可以配置暗色主题,

    //和 CompositionLocal<Colors> 命名相似的是 ThreadLocal, 不同的线程使用 ThreadLocal 类型的变量都是拿到不同的对象
    //例如 val intThreadLocal: ThreadLocal<Int> //线程 1 中改为 1, 线程 2 中改为 2, 再次返回到线程 1 中取到的值仍然为 1
    TabItem(iconId = R.drawable.ic_chat_outlined,
        title = "聊天",
        tint = WeComposeTheme.colors.icon,
        Modifier
    )
}