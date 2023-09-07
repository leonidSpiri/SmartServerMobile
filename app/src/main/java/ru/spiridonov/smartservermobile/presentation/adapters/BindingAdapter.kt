package ru.spiridonov.smartservermobile.presentation.adapters

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.spiridonov.smartservermobile.R
import ru.spiridonov.smartservermobile.domain.entity.DevTypes
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices

@BindingAdapter("setRealTemp")
fun setRealTemp(textView: TextView, raspState: List<Pair<RaspDevices, String>>?) {
    val realTemp = raspState?.find { it.first.devType == DevTypes.TEMP_SENSOR }?.second
        ?: "Ошибка получения данных"
    textView.text =
        String.format(textView.context.resources.getString(R.string.real_temp), realTemp)
}

@BindingAdapter("setRealBoxTemp")
fun setRealBoxTemp(textView: TextView, raspState: List<Pair<RaspDevices, String>>?) {
    val realTemp = raspState?.find { it.first.devType == DevTypes.BOX_TEMP_SENSOR }?.second
        ?: "Ошибка получения данных"
    textView.text =
        String.format(textView.context.resources.getString(R.string.real_box_temp), realTemp)
}

@BindingAdapter("setRealFan")
fun setRealFan(textView: TextView, raspState: List<Pair<RaspDevices, String>>?) {
    val realTemp = raspState?.find { it.first.devType == DevTypes.FAN }?.second.toBoolean()
    textView.text = if (realTemp) "Вентилятор включен" else "Вентилятор выключен"
    textView.setTextColor(if (realTemp) Color.GREEN else Color.YELLOW)
}

@BindingAdapter("setSecurityViolated")
fun setSecurityViolated(textView: TextView, isSecurityViolated: Boolean) {
    textView.text = if (isSecurityViolated) "Нарушена безопасность периметра" else "Безопасность периметра в норме"
    textView.setTextColor(if (isSecurityViolated) Color.RED else Color.GREEN)
}