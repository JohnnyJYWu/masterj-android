package com.masterj.demo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun EnglishSentenceComposeText(viewModel: MainViewModel) {
    val state by viewModel.getDataFlow().collectAsState()
    EnglishSentenceComposeText(
        text = state.pageData.englishText,
        underlineTextList = state.pageData.underlineContents?.filter { it.type != 0 }?.map {
            Triple(it.index, it.index + it.length, Color(0xFFFA5151))
        },
        backgroundTextList = state.pageData.underlineContents?.filter { it.type == 0 }?.map {
            Triple(it.index, it.index + it.length, Color(0x4DFA5151))
        },
        coloredText = state.pageData.word?.getOrNull(state.pageData.wordIndex)?.let {
            Triple(it.index, it.index + it.length, Color(0xFF8072FF))
        }
    )
}

@Composable
private fun EnglishSentenceComposeText(
    text: String,
    underlineTextList: List<Triple<Int, Int, Color>>? = null,
    backgroundTextList: List<Triple<Int, Int, Color>>? = null,
    coloredText: Triple<Int, Int, Color>? = null
) {
    var onDraw: DrawScope.() -> Unit by remember { mutableStateOf({}) }
    val path by remember { mutableStateOf(Path()) }
    Text(
        text = buildAnnotatedString {
            append(text)
            coloredText?.let {
                addStyle(
                    style = SpanStyle(color = it.third),
                    start = it.first,
                    end = it.second
                )
            }
        },
        style = TextStyle(
            color = Color(0xFF191919),
            fontSize = dpToSp(24.dp),
            lineHeight = dpToSp(28.dp)
        ),
        modifier = Modifier.drawBehind {
            onDraw()
        },
        onTextLayout = { textLayoutResult ->
            onDraw = {
                backgroundTextList?.forEach {
                    val start = it.first
                    val end = if (it.second < text.length) {
                        it.second
                    } else {
                        it.second - 1
                    }
                    for (index in start until end) {
                        val boundsRect = textLayoutResult.getBoundingBox(index)
                        drawRect(
                            brush = SolidColor(it.third),
                            topLeft = boundsRect.topLeft,
                            size = boundsRect.size
                        )
                    }
                }
                underlineTextList?.forEach {
                    val start = it.first
                    val end = if (it.second < text.length) {
                        it.second
                    } else {
                        it.second - 1
                    }
                    val boundsRectStart = textLayoutResult.getBoundingBox(start)
                    val boundsRectEnd = textLayoutResult.getBoundingBox(end)
                    drawPath(
                        path = path.buildWaveLinePath(
                            start = boundsRectStart.left,
                            end = boundsRectEnd.right,
                            y = boundsRectStart.bottom,
                            waveLength = 24f * 3 / 2
                        ),
                        color = it.third,
                        style = Stroke(
                            width = 4F
                        )
                    )
                }
            }
        }
    )
}

private fun Path.buildWaveLinePath(start: Float, end: Float, y: Float, waveLength: Float): Path {
    asAndroidPath().rewind()
    val TWO_PI = 2 * Math.PI.toFloat()
    var x = start
    while (x < end) {
        val offsetY = y + sin(((x - start) / waveLength) * TWO_PI) * 5
        if (x == start) {
            moveTo(start, offsetY)
        }
        lineTo(x, offsetY)
        x += 1F
    }
    return this
}
