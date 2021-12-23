package com.example.gameroom.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameroom.R
import com.example.gameroom.controller.Controller
import com.example.gameroom.databinding.ToyItemBinding
import com.example.gameroom.model.Toy
class ToyAdapter: RecyclerView.Adapter<ToyAdapter.ToyHolder>() {

    private var toyList = ArrayList<Toy>()

    class ToyHolder(item :View):RecyclerView.ViewHolder(item) {
        val binding = ToyItemBinding.bind(item)
        val imButton =binding.imButtonSell

        fun bind(toy: Toy) = with(binding){

            textName.text = toy.name
            textWeight.text = "${toy.weight}"
            textColor.text = toy.color
            textSize.text = toy.size
            textPrice.text= "${toy.price}"

        }

   }

    //надуває шаблон - toy_item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.toy_item, parent, false)
        return ToyHolder(view)
    }

    //заповлюємо шаблон
    override fun onBindViewHolder(holder: ToyHolder, position: Int) {
        holder.bind(toyList[position])

        //продавання іграшки
        holder.imButton.setOnClickListener{

            Controller.addMoney(toyList[position].price!!) //додаэмо гроші на баланс
            Controller.getToysInTheToyStore().add(toyList[position]) //додаємо іграшку в магазин
            Controller.getToysInTheRoom().remove(toyList[position]) //видаляємо з кімнати

            toyList.removeAt(position)  //видаляємо з списка
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return toyList.size
    }

    fun update(){
        toyList.clear()
        toyList.addAll(Controller.getToysInTheRoom())
        notifyDataSetChanged()
    }
    fun add(toys:ArrayList<Toy>){
        toyList.clear()
        toyList.addAll(toys)
        notifyDataSetChanged()
    }
}