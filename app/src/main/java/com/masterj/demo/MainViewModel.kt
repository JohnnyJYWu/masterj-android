package com.masterj.demo

import androidx.compose.runtime.mutableStateOf
import com.masterj.base.MasterJBase
import com.masterj.base.arch.PageData
import com.masterj.base.arch.PageViewModel
import com.masterj.base.data.BaseData
import com.masterj.base.third.json.JsonMapperByGson
import com.masterj.base.utils.StreamUtils

/**
 * Created by Wu Jingyuan on 2023/10/8
 */
class MainViewModel : PageViewModel<MainUiState, MainUiEvent>() {
    init {
        val data = JsonMapperByGson.parseJsonObject(
            StreamUtils.get(MasterJBase.getApplication(), R.raw.text),
            EnglishPictureImageDataVO::class.java
        )
        setData(
            getPageData().copy(
                englishText = data?.text?.englishText ?: "",
                word = data?.text?.word,
                underlineContents = data?.text?.underlineContents
            )
        )
    }

    var test = mutableStateOf(0)

    override fun createInitialData(): PageData<MainUiState> {
        return PageData(pageData = MainUiState())
    }

    fun click() {
        updateData(getPageData().copy(clickTimes = getPageData().clickTimes + 1))
    }

    fun nextWord() {
        val wordIndex = getPageData().wordIndex
        getPageData().word?.let {
            updateData(
                getPageData().copy(
                    wordIndex = if (wordIndex < it.size - 1) {
                        wordIndex + 1
                    } else {
                        0
                    }
                )
            )
        }
    }
}

data class MainUiState(
    val title: String = "Hello World!",
    val clickTimes: Int = 0,

    val englishText: String = "",
    val word: List<EnglishPictureImageWordDataVO>? = null,
    val wordIndex: Int = 0, // 下标
    val underlineContents: List<EnglishUnderlineContent>? = null
) : BaseData()

sealed class MainUiEvent : BaseData()
