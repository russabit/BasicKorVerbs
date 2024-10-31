package com.example.basickorverbs.secondScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Meaning

class SecondScreenAdapter(
    private val verbId: Int,
    private val itemList: List<Meaning>
) : RecyclerView.Adapter<SecondScreenAdapter.ViewHolder>() {

    // Внутренний класс ViewHolder описывает элемент списка
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val engTranslTextView: TextView = itemView.findViewById(R.id.detail_verb_item_eng_transl)
        val rusTranslTextView: TextView = itemView.findViewById(R.id.detail_verb_item_rus_transl)
        //val antonymTextView: TextView = itemView.findViewById(R.id.detail_verb_item_antonym)
    }

    // Создаём новую разметку для каждого элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_verb_list_item_layout, parent, false)
        return ViewHolder(view)
    }

    // Привязываем данные к элементу списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // to change in settings to rusTranslation if Russian is the main language
        holder.engTranslTextView.text = "· " + itemList[position].engTranslation

        holder.rusTranslTextView.text = "· " + itemList[position].rusTranslation

/*        holder.antonymTextView.text = "반의어(反義語): " + ViewModelProvider(holder.itemView.findFragment()).get(
            MainActivityViewModel::class.java).verbsList.find {
            it.id == itemList[position].antonymId }?.writing*/

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("verbId", verbId)
            bundle.putInt("meaningPosition", position)

            findNavController(holder.engTranslTextView)
                .navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
        }
    }

    // Возвращаем количество элементов в списке
    override fun getItemCount() = itemList.size
}