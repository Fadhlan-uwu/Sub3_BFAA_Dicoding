package com.dicoding.githubappsub3.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.githubappsub3.alarm.AlarmReceiver
import com.dicoding.githubappsub3.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private var title = "Settings"

    companion object {
        const val SHARED_PREFERENCES = "shared_preferences"
        const val BOOLEAN_KEY = "boolean_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle(title)

        alarmReceiver = AlarmReceiver()

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val getBool = sharedPreferences.getBoolean(BOOLEAN_KEY, false)
        binding.cbAlarmReminder.isChecked = getBool

        binding.cbAlarmReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val editor = sharedPreferences.edit()
                editor.apply {
                    putBoolean(BOOLEAN_KEY, true)
                }.apply()

                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.EXTRA_TYPE, "09:00")

            } else {
                val editor = sharedPreferences.edit()
                editor.apply{
                    putBoolean(BOOLEAN_KEY, false)
                }.apply()

                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
            }
        }

    }
    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }
}