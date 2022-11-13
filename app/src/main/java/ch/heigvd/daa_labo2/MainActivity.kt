package ch.heigvd.daa_labo2

import android.icu.text.DateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import ch.heigvd.daa_labo2.Person.Companion.exampleStudent
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker

class MainActivity : AppCompatActivity() {
    private lateinit var btnDatePicker: ImageButton

    private lateinit var baseInputsGroup: Group
    private lateinit var studentInputsGroup: Group
    private lateinit var workerInputsGroup: Group

    private lateinit var cancelBtn: Button
    private lateinit var confirmBtn: Button

    private lateinit var formState: FormState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker = findViewById(R.id.date_picker_actions)

        baseInputsGroup = findViewById(R.id.main_base_group)
        studentInputsGroup = findViewById(R.id.student_specific_group)
        workerInputsGroup = findViewById(R.id.worker_specific_group)

        cancelBtn = findViewById(R.id.cancel_button)
        confirmBtn = findViewById(R.id.ok_button)

        formState = FormState(
            //main
            findViewById(R.id.main_base_name_edit),
            findViewById(R.id.main_base_firstname_edit),
            findViewById(R.id.main_base_birthdate_edit),
            findViewById(R.id.main_base_nationality_spinner),
            findViewById(R.id.main_base_occupation_radio_group),
            //student
            findViewById(R.id.student_school_edit),
            findViewById(R.id.student_grad_year_edit),
            //worker
            findViewById(R.id.worker_enterprise_edit),
            findViewById(R.id.worker_sector_spinner),
            findViewById(R.id.worker_experience_edit),
            //additional
            findViewById(R.id.additional_email_edit),
            findViewById(R.id.additional_remarks_edit),
        )

        formState.spinnerNationality.adapter = DefaultValueAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getString(R.string.nationality_empty),
            resources.getStringArray(R.array.nationalities)
        )

        formState.spinnerWorkerSector.adapter = DefaultValueAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getString(R.string.sectors_empty),
            resources.getStringArray(R.array.sectors)
        )

        formState.inputOccupation.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.main_base_radio_student -> {
                    studentInputsGroup.visibility = Group.VISIBLE
                    workerInputsGroup.visibility = Group.GONE
                }
                R.id.main_base_radio_employee -> {
                    studentInputsGroup.visibility = Group.GONE
                    workerInputsGroup.visibility = Group.VISIBLE
                }
                else -> {
                    studentInputsGroup.visibility = Group.GONE
                    workerInputsGroup.visibility = Group.GONE
                }
            }
        }

        btnDatePicker.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                openDatePicker()
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

        formState.hydrate(exampleStudent) // hydrate the form with an example worker
    }

    private fun openDatePicker() {
        val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG, resources.configuration.locales.get(0)).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        var currentTimestamp = MaterialDatePicker.todayInUtcMilliseconds()

        try {
            currentTimestamp = dateFormatter.parse(formState.inputBirthDate.text.toString())!!.time
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.main_base_birthdate_dialog_title)
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setOpenAt(currentTimestamp)
                    .setValidator(DateValidatorPointBackward.now())
                    .setStart(Calendar.getInstance().apply {
                        set(Calendar.YEAR, utcCalendar.get(Calendar.YEAR) - 110)
                    }.timeInMillis)
                    .setEnd(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            )
            .setSelection(currentTimestamp)
            .build()

        datePicker.show(supportFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener {
            utcCalendar.timeInMillis = it
            formState.inputBirthDate.setText(dateFormatter.format(utcCalendar.time))

            formState.spinnerNationality.requestFocusFromTouch()
            formState.spinnerNationality.performClick()
        }
    }

    private fun clearForm() {
        formState.clearAll()
    }

    private fun save() {
        try {
            Toast.makeText(this, formState.export().toString(), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}