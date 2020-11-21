package com.example.accountingfinances

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.item_rv.view.*
import java.lang.reflect.Type

class CustomAdapter(private val achievementsList: ArrayList<Achievement>) :
    RecyclerView.Adapter<CustomAdapter.AchievementViewHolder>() {

    inner class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etName : EditText = itemView.et_name
        val etScore : EditText = itemView.et_score
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return AchievementViewHolder(v)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.etName.setText(achievementsList[position].achievement)
        holder.etScore.setText(achievementsList[position].score.toString())
    }

    override fun getItemCount(): Int = achievementsList.size
}