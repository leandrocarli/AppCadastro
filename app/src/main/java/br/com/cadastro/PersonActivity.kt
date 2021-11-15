package br.com.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import br.com.cadastro.db.DatabaseHandler
import br.com.cadastro.function.PeriodClass
import br.com.cadastro.model.Person
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : AppCompatActivity() {

    var databaseHandler = DatabaseHandler(this)
    var person: Person? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        val arraySpinner = listOf<String>(getString(R.string.male),getString(R.string.female))
        val arraySpinnerAdapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraySpinner)
        spnGender.adapter = arraySpinnerAdapter

        if(intent.getStringExtra("mode") == "Edit"){
            person = databaseHandler.getPerson(intent.getIntExtra("id",0))
            tvTitle.text = getString(R.string.editPerson)
            etName.setText(person!!.name)
            etBirth.setText(person!!.birth)
            spnGender.setSelection(arraySpinnerAdapter.getPosition(person!!.gender))
            btnDel.setOnClickListener{
                databaseHandler.delPerson(person!!.id)
                finish()
            }
        }
        else{
            btnDel.visibility = View.GONE
        }
        btnSave.setOnClickListener {
            if(testData()){
                if(intent.getStringExtra("mode") == "Edit"){
                    person = populatePerson(person)
                    databaseHandler.updatePerson(person!!)
                }
                else{
                    person = populatePerson(null)
                    databaseHandler.addPerson(person!!)
                }
                finish()
            }
            else{
                Toast.makeText(this, R.string.errorData, Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun testData(): Boolean{
        return etName.text.toString() != "" && etBirth.text.toString().length == 10 && PeriodClass().checkPeriod(etBirth.text.toString())
    }
    private fun populatePerson(p0: Person?): Person{
        val person = Person()
        if(p0 != null) person.id = p0!!.id
        person.name = etName.text.toString()
        person.birth = etBirth.text.toString()
        person.gender = spnGender.selectedItem.toString()
        return person
    }
}