package br.com.cadastro.function

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.time.LocalDate
import java.time.Period

class PeriodClass {
    @RequiresApi(Build.VERSION_CODES.O)
    fun period(str: String): Period? {
        val now = LocalDate.now()
        val year = str.split("/")
        var date: LocalDate? = null
        try{
            date = LocalDate.of(year[2].toInt(),year[1].toInt(),year[0].toInt())
        }catch (e: Exception){
            Log.i("Erro data: ", e.toString())
            return null
        }
        val period = Period.between(date,now)
        Log.i("Period: ", period.toString())
        return period
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPeriod(str: String): Boolean {
        return period(str) != null && period(str)!!.days >=0
    }
}