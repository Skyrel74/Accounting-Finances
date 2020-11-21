package com.example.accountingfinances

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_accounting.*
import java.lang.reflect.Type
import java.util.*

class AccountActivity : AppCompatActivity() {

    val MONEY_PREF_NAME = "Money"
    val TRATA_PREF_NAME = "TRATA"
    val INCOME_PREF_NAME = "INCOME"
    val ACHIEVE_PREF_NAME = "Achievements"

    private val tvArr = IntArray(5)
    private val evArr = IntArray(5)
    var goalsArray = arrayListOf<Achievement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounting)

        val moneySp : SharedPreferences = getSharedPreferences(MONEY_PREF_NAME, MODE_PRIVATE)
        val trataSp : SharedPreferences = getSharedPreferences(TRATA_PREF_NAME, MODE_PRIVATE)
        val incomeSp : SharedPreferences = getSharedPreferences(INCOME_PREF_NAME, MODE_PRIVATE)
        val goalSp : SharedPreferences = getSharedPreferences(ACHIEVE_PREF_NAME, MODE_PRIVATE)

        if (moneySp.contains(MONEY_PREF_NAME)) {
            val savedString = moneySp.getString(MONEY_PREF_NAME, "")
            val st = StringTokenizer(savedString, ",")
            for (i in 0..4) {
                tvArr[i] = (st.nextToken()).toInt()
            }
            Tv1.text = tvArr[0].toString()
            Tv2.text = tvArr[1].toString()
            Tv3.text = tvArr[2].toString()
            Tv4.text = tvArr[3].toString()
            Tv5.text = tvArr[4].toString()
        }

        if (trataSp.contains(TRATA_PREF_NAME)) {
            val savedString = trataSp.getString(TRATA_PREF_NAME, "")
            val st = StringTokenizer(savedString, ",")
            for (i in 0..4) {
                evArr[i] = (st.nextToken()).toInt()
            }
            Et1.setText(evArr[0].toString())
            Et2.setText(evArr[1].toString())
            Et3.setText(evArr[2].toString())
            Et4.setText(evArr[3].toString())
            Tv1.text = (tvArr[0] - evArr[0]).toString()
            Tv2.text = (tvArr[1] - evArr[1]).toString()
            Tv3.text = (tvArr[2] - evArr[2]).toString()
            Tv4.text = (tvArr[3] - evArr[3]).toString()
        }

        if (incomeSp.contains(INCOME_PREF_NAME))
            allMoneyEt.setText(incomeSp.getString(INCOME_PREF_NAME, ""))

        if (goalSp.contains(ACHIEVE_PREF_NAME)) {
            val gson = Gson()
            val json = goalSp.getString(ACHIEVE_PREF_NAME, "")
            val type : Type = object : TypeToken<ArrayList<Achievement>>() {}.type
            goalsArray = gson.fromJson(json, type)
        }

        allMoneyEt.doOnTextChanged { text, _, _, _ ->
            val allMoney: Int = text.toString().toInt()
            Tv1.text = (allMoney * 0.5).toInt().toString()
            Tv2.text = (allMoney * 0.2).toInt().toString()
            Tv3.text = (allMoney * 0.15).toInt().toString()
            Tv4.text = (allMoney * 0.05).toInt().toString()
            Tv5.text = (allMoney * 0.1).toInt().toString()
            tvArr[0] = (allMoney * 0.5).toInt()
            tvArr[1] = (allMoney * 0.2).toInt()
            tvArr[2] = (allMoney * 0.15).toInt()
            tvArr[3] = (allMoney * 0.05).toInt()
            tvArr[4] = (allMoney * 0.1).toInt()

            var str = ""
            for (i in 0..4) {
                str += tvArr[i].toString() + ","
            }

            moneySp
                .edit()
                .putString(MONEY_PREF_NAME, str)
                .apply()
        }

        Et1.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    evArr[0] = text.toString().toInt()
                    Tv1.text = (tvArr[0] - evArr[0]).toString()
                }
                else
                    Tv1.text = tvArr[0].toString()
            }
        }

        Et2.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    evArr[1] = text.toString().toInt()
                    Tv2.text = (tvArr[1] - evArr[1]).toString()
                }
                else
                    Tv2.text = tvArr[1].toString()
            }
        }

        Et3.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    evArr[2] = text.toString().toInt()
                    Tv3.text = (tvArr[2] - evArr[2]).toString()
                }
                else
                    Tv3.text = tvArr[2].toString()
            }
        }

        Et4.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    evArr[3] = text.toString().toInt()
                    Tv4.text = (tvArr[3] - evArr[3]).toString()
                }
                else
                    Tv4.text = tvArr[3].toString()
            }
        }

        Rv.layoutManager = LinearLayoutManager(this)
        val adaptor = CustomAdapter(goalsArray)
        Rv.adapter = adaptor

        add_item.setOnClickListener {
            goalsArray.add(Achievement())
            adaptor.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        super.onStop()

        val trataSp : SharedPreferences = getSharedPreferences(TRATA_PREF_NAME, MODE_PRIVATE)
        var str = ""
        for (i in 0..4) {
            str += evArr[i].toString() + ","
        }

        trataSp
            .edit()
            .putString(TRATA_PREF_NAME, str)
            .apply()

        val goalSp : SharedPreferences = getSharedPreferences(ACHIEVE_PREF_NAME, MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(goalsArray)

        goalSp
            .edit()
            .putString(ACHIEVE_PREF_NAME, json)
            .apply()
    }
}