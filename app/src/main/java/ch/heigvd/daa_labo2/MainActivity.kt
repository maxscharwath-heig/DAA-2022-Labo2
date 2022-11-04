package ch.heigvd.daa_labo2

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var btnDatePicker: ImageButton
    private lateinit var inputDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker = findViewById(R.id.date_picker_actions)
        inputDate = findViewById(R.id.main_base_birthdate_edit)


        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        btnDatePicker.setOnClickListener {
            var currentTimestamp = MaterialDatePicker.todayInUtcMilliseconds()

            try {
                currentTimestamp = dateFormatter.parse(inputDate.text.toString())!!.time
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setOpenAt(currentTimestamp)
                        .build()
                )
                .setSelection(currentTimestamp)
                .build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                val date = dateFormatter.format(Date(it))
                inputDate.setText(date)
            }
        }

    }
}