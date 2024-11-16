package com.example.lab2app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2app.R
import com.example.lab2app.model.Animal

class AnimalAdapter : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {
    private val animalList = mutableListOf<Animal>()
    private val animals = mutableListOf<Animal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    fun setAnimals(newAnimals: List<Animal>) {
        animals.clear()
        animals.addAll(newAnimals)
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalList[position]
        holder.name.text = animal.name
        holder.kingdom.text = "Kingdom: ${animal.taxonomy.kingdom}"
        holder.family.text = "Family: ${animal.taxonomy.family}"
        holder.locations.text = "Locations: ${animal.locations.joinToString(", ")}"
        holder.diet.text = "Diet: ${animal.diet}"
    }

    override fun getItemCount(): Int = animalList.size

    fun updateList(newList: List<Animal>) {
        val diffCallback = AnimalDiffUtil(animalList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        animalList.clear()
        animalList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class AnimalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.animalName)
        val kingdom: TextView = view.findViewById(R.id.textKingdom)
        val family: TextView = view.findViewById(R.id.textFamily)
        val locations: TextView = view.findViewById(R.id.textLocations)
        val diet: TextView = view.findViewById(R.id.textDiet)
        val lifespan: TextView = itemView.findViewById(R.id.animalLifespan)
        val location: TextView = view.findViewById(R.id.animalLocation)
    }
}

class AnimalDiffUtil(
    private val oldList: List<Animal>,
    private val newList: List<Animal>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

