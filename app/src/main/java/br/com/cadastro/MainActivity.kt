package br.com.cadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cadastro.adapter.PersonListAdapter
import br.com.cadastro.db.DatabaseHandler
import br.com.cadastro.model.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var personList = ArrayList<Person>()
    var personListAdapter: PersonListAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var databaseHandler = DatabaseHandler(this)
    var search: Boolean = false
    var searchStr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabPerson.setOnClickListener {
            val intent = Intent(this, PersonActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)
        val searchPerson = menu.findItem(R.id.searchPerson)
        val searchView = searchPerson.actionView as SearchView
        searchView.queryHint = getString(R.string.searchTitle)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.toString().isNotEmpty()) {
                    search = true
                    searchStr = p0!!
                }
                else{
                    search = false
                    searchStr = ""
                }
                initView()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume(){
        super.onResume()
        initView()
    }
    private fun initView(){
        personList = if(!search) databaseHandler.getPersonList()
        else databaseHandler.searchPerson(searchStr)
        personListAdapter = PersonListAdapter(personList, this, this::editPerson)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = personListAdapter
    }
    private fun editPerson(id: Int){
        val intent = Intent(this, PersonActivity::class.java)
        intent.putExtra("mode", "Edit")
        intent.putExtra("id", id)
        startActivity(intent)
    }
}