package com.example.basickorverbs.firstScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Verb

class FirstScreenAdapter(
    private val itemList: List<Verb>,
) : RecyclerView.Adapter<FirstScreenAdapter.ViewHolder>() {

    // Внутренний класс ViewHolder описывает элемент списка
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.verb_item_text)
    }

    // Создаём новую разметку для каждого элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.verb_list_item_layout, parent, false)
        return ViewHolder(view)
    }

    // Привязываем данные к элементу списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = itemList[position].writing
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("verbPosition", position)
            findNavController(holder.textView).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
    }

    // Возвращаем количество элементов в списке
    override fun getItemCount() = itemList.size
}