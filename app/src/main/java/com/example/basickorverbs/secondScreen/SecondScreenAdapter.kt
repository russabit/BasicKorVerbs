package com.example.basickorverbs.secondScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Meaning
import com.example.basickorverbs.domain.Verb

class SecondScreenAdapter(
    private val itemList: List<Meaning>
) : RecyclerView.Adapter<SecondScreenAdapter.ViewHolder>() {

    // Внутренний класс ViewHolder описывает элемент списка
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.detail_verb_item_text)
    }

    // Создаём новую разметку для каждого элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_verb_list_item_layout, parent, false)
        return ViewHolder(view)
    }

    // Привязываем данные к элементу списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = itemList[position].engTranslation // to change in settings to rusTranslation if Russian is the main language
        holder.textView.setOnClickListener { findNavController(holder.textView).navigate(R.id.action_SecondFragment_to_ThirdFragment) }
    }

    // Возвращаем количество элементов в списке
    override fun getItemCount() = itemList.size
}