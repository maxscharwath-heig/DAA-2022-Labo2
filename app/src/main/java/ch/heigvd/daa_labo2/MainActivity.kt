package ch.heigvd.daa_labo2

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import ch.heigvd.daa_labo2.Person.Companion.exampleWorker
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var btnDatePicker: ImageButton
    private lateinit var inputDate: EditText

    private lateinit var baseInputsGroup: Group
    private lateinit var studentInputsGroup: Group
    private lateinit var workerInputsGroup: Group

    private lateinit var cancelBtn: Button
    private lateinit var confirmBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker = findViewById(R.id.date_picker_actions)
        inputDate = findViewById(R.id.main_base_birthdate_edit)

        baseInputsGroup = findViewById(R.id.main_base_group)
        studentInputsGroup = findViewById(R.id.student_specific_group)
        workerInputsGroup = findViewById(R.id.worker_specific_group)

        cancelBtn = findViewById(R.id.cancel_button)
        confirmBtn = findViewById(R.id.ok_button)

        val radio = findViewById<RadioGroup>(R.id.main_base_occupation_radio_group)

        radio.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.main_base_radio_student -> {
                    studentInputsGroup.visibility = Group.VISIBLE
                    workerInputsGroup.visibility = Group.GONE
                }
                R.id.main_base_radio_employee -> {
                    studentInputsGroup.visibility = Group.GONE
                    workerInputsGroup.visibility = Group.VISIBLE
                }
            }
        }

        btnDatePicker.setOnClickListener {
            openDatePicker()
        }

        cancelBtn.setOnClickListener {
            clearForm()
        }

        confirmBtn.setOnClickListener {
            save()
        }
    }

    private fun openDatePicker() {
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        var currentTimestamp = MaterialDatePicker.todayInUtcMilliseconds()

        try {
            currentTimestamp = dateFormatter.parse(inputDate.text.toString())!!.time
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.main_base_birthdate_dialog_title)
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

    private fun clearForm() {
        // TODO: clear the form using
        // text.clear()
        // radiogroup.clearCheck()
        // spinner.setSelection(0)
    }

    private fun save() {
        Toast.makeText(this, exampleWorker.toString(), Toast.LENGTH_LONG).show()
    }
}