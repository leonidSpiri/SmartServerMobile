package ru.spiridonov.smartservermobile.presentation.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import ru.spiridonov.smartservermobile.R
import ru.spiridonov.smartservermobile.domain.entity.DevTypes
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
import ru.spiridonov.smartservermobile.domain.entity.Security
import java.time.OffsetDateTime


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

@BindingAdapter("setRealCond")
fun setRealCond(textView: TextView, raspState: List<Pair<RaspDevices, String>>?) {
    val context = textView.context
    val realTemp = raspState?.find { it.first.devType == DevTypes.CONDITIONER }?.second.toBoolean()
    textView.text =
        if (realTemp) context.getString(R.string.cond_on) else context.getString(R.string.cond_off)
    textView.setTextColor(
        if (realTemp) context.resources.getColor(
            R.color.green,
            context.theme
        ) else Color.RED
    )
}

@BindingAdapter("setRealFan")
fun setRealFan(textView: TextView, raspState: List<Pair<RaspDevices, String>>?) {
    val context = textView.context
    val realTemp = raspState?.find { it.first.devType == DevTypes.FAN }?.second.toBoolean()
    textView.text =
        if (realTemp) context.getString(R.string.fan_on) else context.getString(R.string.fan_off)
    textView.setTextColor(
        if (realTemp) context.resources.getColor(
            R.color.green,
            context.theme
        ) else Color.RED
    )
}

@BindingAdapter("setSecurityViolated")
fun setSecurityViolated(textView: TextView, isSecurityViolated: Boolean) {
    val context = textView.context
    textView.text =
        if (isSecurityViolated) context.getString(R.string.security_is_violated) else context.getString(
            R.string.security_is_not_violated
        )
    textView.setTextColor(
        if (isSecurityViolated) Color.RED else context.resources.getColor(
            R.color.green,
            context.theme
        )
    )
}

@BindingAdapter("setSecurityState")
fun setSecurityState(textView: TextView, security: Security?) =
    security?.let {
        val context = textView.context
        val text =
            if (security.isSecurityTurnOn) context.getString(R.string.on_security) else context.getString(
                R.string.off_security
            )
        with(security.dateTime) {
            val date = "$hour:$minute $dayOfMonth-$monthValue-$year"
            textView.text =
                context.getString(R.string.security_set_by_user, text, security.user.userName, date)
            textView.setTextColor(
                if (security.isSecurityTurnOn) context.resources.getColor(
                    R.color.green,
                    context.theme
                ) else Color.RED
            )
        }

    }

@BindingAdapter("setButtonSecurityState")
fun setButtonSecurityState(btn: MaterialButton, security: Security?) =
    security?.let {
        val context = btn.context
        btn.text =
            if (security.isSecurityTurnOn) context.getString(R.string.set_off_security) else context.getString(
                R.string.set_on_security
            )
        btn.setTextColor(
            if (security.isSecurityTurnOn) Color.RED else context.resources.getColor(
                R.color.green,
                context.theme
            )
        )
        btn.strokeColor =
            (if (security.isSecurityTurnOn) ColorStateList.valueOf(Color.RED) else ColorStateList.valueOf(
                context.resources.getColor(
                    R.color.green,
                    context.theme
                )
            ))
    }

@BindingAdapter("parseOffsetDateTime")
fun parseOffsetDateTime(textView: TextView, date: OffsetDateTime) {
    val month =
        if (date.monthValue >= 10) date.monthValue.toString() else "0${date.monthValue}"
    val day =
        if (date.dayOfMonth >= 10) date.dayOfMonth.toString() else "0${date.dayOfMonth}"
    val hour = if (date.hour >= 10) date.hour.toString() else "0${date.hour}"
    val minute = if (date.minute >= 10) date.minute.toString() else "0${date.minute}"
    textView.text = textView.context.getString(
        R.string.time_date,
        hour,
        minute,
        day,
        month,
        date.year.toString()
    )

}