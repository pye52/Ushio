package com.kanade.ushio.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import java.util.regex.Pattern

const val APP_ID = "bgm1535abdd9b4406b1"
const val APP_SECRET = "d387ed984cc76959efcc2376459d32fd"
const val REDIRECT_URI = "ushio://tomoyo"
const val UPDATE_PATH = "https://raw.githubusercontent.com/pye52/Ushio/master/update.json"

/**
 * 筛选两个字符串，若[name1]为空则采用[name2]
 *
 * 并保留[length]长度，超过长度以xxx...表示
 * @param name1 优先串
 * @param name2 备用串
 */
fun strFilter(name1: String?, name2: String?, length: Int): String {
    if (name1 == null && name2 == null) {
        return ""
    }
    var name = if (TextUtils.isEmpty(name1)) name2 else name1
    if (name!!.length > length) name = "${name.substring(0, length)}..."
    return name
}

/**
 * 匹配字符串中是否包含"目标字符"，若包含，则设置其颜色
 * @param 源字符串
 * @param 匹配的目标串
 */
fun stringFormat(s: String, key: String): SpannableString {
    // 忽略大小写
    val p = Pattern.compile(key, Pattern.CASE_INSENSITIVE)
    val m = p.matcher(s)
    val fullnameSpan = SpannableString(s)
    while (m.find()) {
        if (s.contains(m.group())) {
            fullnameSpan.setSpan(ForegroundColorSpan(Color.parseColor("#FFA500")), m.start(),
                    m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
    return fullnameSpan
}