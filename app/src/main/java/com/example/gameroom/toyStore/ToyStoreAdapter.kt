package com.example.gameroom.toyStore


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameroom.R
import com.example.gameroom.controller.Controller
import com.example.gameroom.databinding.ToyStoreItemBinding
import com.example.gameroom.model.Toy

class ToyStoreAdapter(var onClick:onClickBuy): RecyclerView.Adapter<ToyStoreAdapter.ToyHolder>() {

    private var toyList = ArrayList<Toy>()

    interface onClickBuy{
        fun changeMoney(money: Double)
        fun notEnoughMoney()
    }

    class ToyHolder(item : View): RecyclerView.ViewHolder(item) {
        val binding = ToyStoreItemBinding.bind(item)
        val imButton =binding.imButtonBuy

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.toy_store_item, parent, false)
        return ToyHolder(view)
    }

    //заповлюємо шаблон
    override fun onBindViewHolder(holder: ToyHolder, position: Int) {
        holder.bind(toyList[position])

        //купівля іграшки
        holder.imButton.setOnClickListener{

            if(toyList[position]?.price!! <= Controller.getMoney() ){
                Controller.subMoney(toyList[position].price!!)
                Controller.getToysInTheRoom().add(toyList[position])
                Controller.getToysInTheToyStore().remove(toyList[position])
                toyList.removeAt(position)
                notifyDataSetChanged()
                onClick.changeMoney(Controller.getMoney())
            }else{
                onClick.notEnoughMoney()
            }


        }

    }

    override fun getItemCount(): Int {
        return toyList.size
    }

    fun addToy(toys:ArrayList<Toy>){
        toyList.addAll(toys)
        notifyDataSetChanged()
    }

    fun update(){
        toyList.clear()
        toyList.addAll(Controller.getToysInTheToyStore())
        notifyDataSetChanged()
    }

    fun deleteToy(index : Int){
        Controller.getToysInTheToyStore().remove(toyList[index])
        toyList.removeAt(index)
    }

}