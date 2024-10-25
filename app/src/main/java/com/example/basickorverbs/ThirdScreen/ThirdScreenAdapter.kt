package com.example.basickorverbs.secondScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Example
import com.example.basickorverbs.domain.Meaning

class ThirdScreenAdapter(
    private val itemList: List<Example>
) : RecyclerView.Adapter<ThirdScreenAdapter.ViewHolder>() {

    // Внутренний класс ViewHolder описывает элемент списка
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val koreanExampleTextView: TextView = itemView.findViewById(R.id.korean_example_text)
        val engTranslExampleTextView: TextView = itemView.findViewById(R.id.example_eng_transl)
        val rusTranslExampleTextView: TextView = itemView.findViewById(R.id.example_rus_transl)
    }

    // Создаём новую разметку для каждого элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meaning_examples_verb_list_item_layout, parent, false)
        return ViewHolder(view)
    }

    // Привязываем данные к элементу списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.koreanExampleTextView.text = itemList[position].korean // to change in settings to rusTranslation if Russian is the main language
        holder.engTranslExampleTextView.text = itemList[position].engTranslation
        holder.rusTranslExampleTextView.text = itemList[position].rusTranslation
    }

    // Возвращаем количество элементов в списке
    override fun getItemCount() = itemList.size
}