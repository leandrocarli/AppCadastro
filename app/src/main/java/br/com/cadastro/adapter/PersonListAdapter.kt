package br.com.cadastro.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.com.cadastro.R
import br.com.cadastro.function.PeriodClass
import br.com.cadastro.model.Person
import kotlinx.android.synthetic.main.content_person.view.*


class PersonListAdapter(personList: ArrayList<Person>, internal var ctx: Context, private val callback: (Int) -> Unit):
    RecyclerView.Adapter<PersonListAdapter.ViewHolder>(){

    private var personList = ArrayList<Person>()
    init {
        this.personList = personList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.content_person,parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = personList[position]

        holder.name.text = person.name
        holder.birth.text = person.birth
        holder.gender.text = person.gender
        holder.age.text = PeriodClass().period(person.birth)!!.years.toString()

        if(person.gender == ctx.getString(R.string.male)) holder.gender.setTextColor(ctx.getColor(R.color.blue))
        else holder.gender.setTextColor(ctx.getColor(R.color.pink))
        if(holder.age.text.toString().toInt() >= 18) holder.age.setTextColor(ctx.getColor(R.color.green))

        holder.lay.setOnClickListener {
            callback(person.id)
        }

        if(position == personList.size-1) holder.endLine.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var lay: LinearLayout = view.layContentPerson
        var name: TextView = view.tvContentName
        var birth: TextView = view.tvContentBirth
        var age: TextView = view.tvContentAge
        var gender: TextView = view.tvContentGender
        var endLine: LinearLayout = view.endLine
    }
}