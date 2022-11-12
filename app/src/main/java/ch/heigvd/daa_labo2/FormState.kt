package ch.heigvd.daa_labo2

import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import java.util.*

data class FormState(
    val inputName: EditText,
    val inputFirstName: EditText,
    val inputBirthDate: EditText,
    val inputNationality: Spinner,
    val inputOccupation: RadioGroup,
    val inputStudentSchool: EditText,
    val inputStudentGradYear: EditText,
    val inputWorkerEnterpriseTitle: EditText,
    val inputWorkerSector: Spinner,
    val inputWorkerExperience: EditText,
    val inputAdditionalEmail: EditText,
    val inputAdditionalRemarks: EditText,
){
    private fun hydrate(person: Person){
        inputName.setText(person.name)
        inputFirstName.setText(person.firstName)
        inputBirthDate.setText(SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(person.birthDay.time))
        //TODO: Spinner  Nationality
        inputAdditionalEmail.setText(person.email)
        inputAdditionalRemarks.setText(person.remark)
    }

    fun hydrate(student: Student){
        inputOccupation.check(R.id.main_base_radio_student)

        hydrate(student as Person)
        inputStudentSchool.setText(student.university)
        inputStudentGradYear.setText(student.graduationYear.toString())
    }

    fun hydrate(worker: Worker) {
        inputOccupation.check(R.id.main_base_radio_employee)

        hydrate(worker as Person)
        inputWorkerEnterpriseTitle.setText(worker.company)
        //TODO: Spinner Sector
        inputWorkerExperience.setText(worker.experienceYear.toString())
    }

    private fun exportStudent(): Student {
        // TODO: Check if the form is valid
        return Student(
            inputName.text.toString(),
            inputFirstName.text.toString(),
            Calendar.getInstance().apply {
                time = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).parse(inputBirthDate.text.toString())!!
            },
            inputNationality.selectedItem.toString(),
            inputStudentSchool.text.toString(),
            inputStudentGradYear.text.toString().toInt(),
            inputAdditionalEmail.text.toString(),
            inputAdditionalRemarks.text.toString()
        )
    }

    private fun exportWorker(): Worker {
        //TODO: Check if the form is valid
        return Worker(
            inputName.text.toString(),
            inputFirstName.text.toString(),
            Calendar.getInstance().apply {
                time = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).parse(inputBirthDate.text.toString())!!
            },
            inputNationality.selectedItem.toString(),
            inputWorkerEnterpriseTitle.text.toString(),
            inputWorkerSector.selectedItem.toString(),
            inputWorkerExperience.text.toString().toInt(),
            inputAdditionalEmail.text.toString(),
            inputAdditionalRemarks.text.toString()
        )
    }

    @Throws(Exception::class)
    fun export(): Person {
        if (!isValid()){
            throw Exception("Form is not valid")
        }
        return when(inputOccupation.checkedRadioButtonId){
            R.id.main_base_radio_student -> exportStudent()
            R.id.main_base_radio_employee -> exportWorker()
            else -> throw Exception("No occupation selected")
        }
    }

    /**
     * Check if the field is valid
     * Set the error message if not valid using hint text as field name
     * @return true if the field is valid
     */
    private fun checkRequiredField(field: EditText): Boolean {
        field.error = null
        if (field.text.isEmpty()){
            field.error = Resources.getSystem().getString(
                R.string.required_field, field.hint ?:
                Resources.getSystem().getString(R.string.unknown_field)
            )
            return false
        }
        return true
    }

    private fun checkSpinnerField(field: Spinner): Boolean {
        return true;
        /**
        if (field.selectedItemPosition == 0){
            return false
        }
        return true
        **/
    }

    private fun isValid(): Boolean {
        var valid = true
        valid = checkRequiredField(inputName) && valid
        valid = checkRequiredField(inputFirstName) && valid
        valid = checkRequiredField(inputBirthDate) && valid
        valid = checkRequiredField(inputAdditionalEmail) && valid
        valid = checkRequiredField(inputAdditionalRemarks) && valid
        valid = checkSpinnerField(inputNationality) && valid

        when (inputOccupation.checkedRadioButtonId) {
            R.id.main_base_radio_student -> {
                valid = checkRequiredField(inputStudentSchool) && valid
                valid = checkRequiredField(inputStudentGradYear) && valid
            }
            R.id.main_base_radio_employee -> {
                valid = checkRequiredField(inputWorkerEnterpriseTitle) && valid
                valid = checkRequiredField(inputWorkerExperience) && valid
                valid = checkSpinnerField(inputWorkerSector) && valid
            }
            else -> {
                valid = false
            }
        }
        return valid
    }

    fun clearAll(){
        inputName.text.clear()
        inputFirstName.text.clear()
        inputBirthDate.text.clear()
        inputNationality.setSelection(0)
        inputOccupation.clearCheck()
        inputStudentSchool.text.clear()
        inputStudentGradYear.text.clear()
        inputWorkerEnterpriseTitle.text.clear()
        inputWorkerSector.setSelection(0)
        inputWorkerExperience.text.clear()
        inputAdditionalEmail.text.clear()
        inputAdditionalRemarks.text.clear()
    }
}