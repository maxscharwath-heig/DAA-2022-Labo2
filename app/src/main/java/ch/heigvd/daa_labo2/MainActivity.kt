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


data class FormState(
    var inputName: EditText,
    var inputFirstName: EditText,
    var inputBirthDate: EditText,
    var inputNationality: Spinner,
    var inputOccupation: RadioGroup,
    var inputStudentSchool: EditText,
    var inputStudentGradYear: EditText,
    var inputWorkerEnterpriseTitle: EditText,
    var inputWorkerSector: Spinner,
    var inputWorkerExperience: EditText,
    var inputAdditionalEmail: EditText,
    var inputAdditionalRemarks: EditText,
)

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
            currentTimestamp = dateFormatter.parse(formState.inputBirthDate.text.toString())!!.time
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
            formState.inputBirthDate.setText(date)
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