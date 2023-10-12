package com.masterj.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.masterj.base.activity.BaseViewActivity
import com.masterj.base.utils.UIUtils
import com.masterj.demo.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : BaseViewActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun needImmerse() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setUpContent()
        setUpEnglishSentenceComposeText()
    }

    private fun initView() {
        binding.apply {
            tvNext.setOnClickListener {
                viewModel.nextWord()
            }
        }
    }

    // TextView
    private fun setUpContent() {
        // setup text
        lifecycleScope.launch {
            viewModel.getDataFlow()
                .map { it.pageData.englishText }
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect {
                    binding.tvContent.text = it
                }
        }
        // setup underline
        lifecycleScope.launch {
            viewModel.getDataFlow()
                .map { it.pageData.underlineContents }
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { list ->
                    binding.tvContent.clearUnderLine()
                    binding.tvContent.clearLineBackground()
                    list?.forEach {
                        if (it.type == 0) {
                            binding.tvContent.setLineBackground(it.index, it.index + it.length)
                        } else {
                            binding.tvContent.setUnderLine(it.index, it.index + it.length)
                        }
                    }
                }
        }
        // setup wordIndex
        lifecycleScope.launch {
            viewModel.getDataFlow()
                .map { it.pageData.wordIndex }
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect {
                    viewModel.getPageData().word?.let { words ->
                        binding.tvContent.setColoredTextInterval(words[it].index, words[it].index + words[it].length)
                    }
                }
        }
    }

    // Compose
    private fun setUpEnglishSentenceComposeText() {
        binding.composeContainer.setContent {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                EnglishSentenceComposeText(viewModel = viewModel)
            }
        }
    }

    // /////////////////////////////////////
    private fun setUpUpgradeDialog() {
        binding.composeContainer.setContent {
            UpgradeDialog(
                viewModel = viewModel,
                content = "当前版本${BuildConfig.VERSION_NAME}\n1、产品体验优化\n2、细节调整优化，长文案样式啦啦啦啦啦啦啦啦啦",
                isForce = false,
                onConfirmClick = {
                    UIUtils.toast("onConfirmClick")
                    viewModel.click()
                },
                onDismiss = {
                    UIUtils.toastBottom("onDismiss")
                }
            )
        }
    }
}

@Composable
private fun UpgradeDialog(
    viewModel: MainViewModel,
    content: String,
    isForce: Boolean,
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.getDataFlow().collectAsState()
    val test by remember { viewModel.test }
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(290.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_upgrade_dialog),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 29.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            bottomStart = 15.dp,
                            bottomEnd = 15.dp
                        )
                    )
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(
                    text = test.toString(),
                    style = TextStyle(
                        color = Color(0xFF1A1A1A),
                        fontSize = dpToSp(19.dp),
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = content,
                    style = TextStyle(
                        color = Color(0xFF5E5E5E),
                        fontSize = dpToSp(13.dp),
                        fontWeight = FontWeight(400),
                        lineHeight = dpToSp(18.dp),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Box(
                    modifier = Modifier
                        .padding(top = 29.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF647CFF),
                                    Color(0xFF8361F9)
                                )
                            ),
                            shape = RoundedCornerShape(23.dp)
                        )
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) {
                            viewModel.test.value++
                            onConfirmClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "立即更新",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = dpToSp(16.dp),
                            fontWeight = FontWeight(600),
                            textAlign = TextAlign.Center
                        )
                    )
                }
                if (!isForce) {
                    Text(
                        text = "以后再说",
                        style = TextStyle(
                            color = Color(0xFF5E5E5E),
                            fontSize = dpToSp(14.dp),
                            fontWeight = FontWeight(600),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 16.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) {
                                onDismiss()
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }
