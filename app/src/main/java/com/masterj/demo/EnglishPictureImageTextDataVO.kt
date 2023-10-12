package com.masterj.demo

import com.masterj.base.data.BaseData

data class EnglishPictureImageDataVO(
    var image: String = "",
    var audio: String = "",
    val text: EnglishPictureImageTextDataVO? = null
) : BaseData()
data class EnglishPictureImageTextDataVO(
    val englishText: String = "",
    val chineseText: String = "",
    val word: List<EnglishPictureImageWordDataVO>? = null,
    val underlineContents: List<EnglishUnderlineContent>? = null
) : BaseData()

data class EnglishPictureImageWordDataVO(
    val begin: Int = 0,
    val end: Int = 0,
    val index: Int = 0,
    val length: Int = 0,
    val name: String = ""
) : BaseData()

data class EnglishUnderlineContent(
    val index: Int = 0, // 下标位置
    val length: Int = 0, // 长度
    val type: Int = 0 // 划词类型，0：单词，1：短语，2：句子
) : BaseData()
