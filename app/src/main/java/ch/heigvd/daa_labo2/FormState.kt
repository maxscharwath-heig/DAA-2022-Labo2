package ch.heigvd.daa_labo2

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
        inputBirthDate.setText(SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(person.birthDay))
        //TODO: Spinner  Nationality
        inputAdditionalEmail.setText(person.email)
        inputAdditionalRemarks.setText(person.remark)
    }

    fun hydrate(student: Student){
        hydrate(student as Person)
        inputStudentSchool.setText(student.university)
        inputStudentGradYear.setText(student.graduationYear.toString())
    }

    fun hydrate(worker: Worker) {
        hydrate(worker as Person)
        inputWorkerEnterpriseTitle.setText(worker.company)
        //TODO: Spinner Sector
        inputWorkerExperience.setText(worker.experienceYear.toString())
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