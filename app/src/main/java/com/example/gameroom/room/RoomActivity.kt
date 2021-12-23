package com.example.gameroom.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameroom.toyStore.ToyStoreActivity
import com.example.gameroom.controller.Controller
import com.example.gameroom.databinding.ActivityRoomBinding
import com.example.gameroom.db.MyDbManager
import com.example.gameroom.db.MyDbNameClass
import com.example.gameroom.model.Toy
import com.example.gameroom.sort.SortByCost
import java.util.*

class RoomActivity : AppCompatActivity() {

    lateinit var binding: ActivityRoomBinding
    private val toyAdapter = ToyAdapter()
    var myDbManager: MyDbManager? = null
    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        myDbManager = MyDbManager(this)
        init()

        binding.textRoomInfo.append(Controller.toString())

    }


    private fun init() = with(binding) {

        rcViewGameRoom.layoutManager = LinearLayoutManager(this@RoomActivity)

        rcViewGameRoom.adapter = toyAdapter
        toyAdapter.update()

        buttonShow.setOnClickListener {
            toyAdapter.update()
            toyAdapter.notifyDataSetChanged()
        }
    }

    fun onClickBack(view: View) {
        if (binding.linearLayoutSeachToys.isVisible) {
            binding.linearLayoutSeachToys.visibility = View.GONE
            binding.buttonShow.visibility = View.VISIBLE
        } else {
            Controller.clearData()
            finish()
        }
    }

    fun onClickBuy(view: View) {
        //створюємо новий інтент і відкриваємо вікно кімнати
        var intent = Intent(this, ToyStoreActivity::class.java)
        launcher?.launch(intent)
    }

    fun onClickSave(view: View) {
        myDbManager?.openDb()   //відкриваємо базу даних

        myDbManager?.deleteToysFromRoom(Controller.getIdRoom()) //видаляємо всі іграшки

        //зберігаємо всі грашки в бд
        Controller.getToysInTheRoom().forEach {
            myDbManager?.insertToysToDb(Controller.getIdRoom(), MyDbNameClass.IN_ROOM.toInt(), it)
        }
        Controller.getToysInTheToyStore().forEach {
            myDbManager?.insertToysToDb(Controller.getIdRoom(), MyDbNameClass.IN_STORE.toInt(), it)
        }
        myDbManager?.upgradeMoney(Controller.getIdRoom(), Controller.getMoney()) //оновлюємо гроші
        myDbManager?.clodeDb() //закриваємо базу даних
    }

    fun onClickSort(view: View) {
        if (Controller.getToysInTheRoom().isEmpty()) {

            Toast.makeText(this, "The list is empty!!!", Toast.LENGTH_LONG).show()

        } else {
            var costComparator = SortByCost()
            Collections.sort(Controller.getToysInTheRoom(), costComparator)
            Toast.makeText(this, "Toys are successfully sorted by price", Toast.LENGTH_SHORT).show()
            toyAdapter.update()
        }
    }

    fun onClickSearchByParam(view: View) = with(binding) {
        if (Controller.getToysInTheRoom().isEmpty()) {

            Toast.makeText(this@RoomActivity, "", Toast.LENGTH_LONG).show()

        } else {
            buttonShow.visibility = View.GONE
            linearLayoutSeachToys.visibility = View.VISIBLE
        }
    }

    fun onClickSearch(view: View) = with(binding) {


        var priceRangeBeg = edPriceRangeBeg.getText().toString().toDoubleOrNull()
        var priceRangeEnd = edPriceRangeEnd.getText().toString().toDoubleOrNull()

        var weightRangeBeg = edWeightRangeBeg.getText().toString().toDoubleOrNull()
        var weightRangeEnd = edWeightRangeEnd.getText().toString().toDoubleOrNull()

        if (priceRangeBeg != null && priceRangeEnd != null && weightRangeBeg != null && weightRangeEnd != null) {
            if (priceRangeBeg < 0 || priceRangeEnd < 0 || weightRangeBeg < 0 || weightRangeBeg < 0) {

                Toast.makeText(this@RoomActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                return@with
            }
        } else {
            Toast.makeText(this@RoomActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
            return@with
        }

        var foundToys = ArrayList<Toy>()

        Controller.getToysInTheRoom().forEach {

            if ((it.price!! >= priceRangeBeg && it.price!! <= priceRangeEnd) &&
                (it.weight!! >= weightRangeBeg && it.weight!! <= weightRangeEnd)
            ) {

                foundToys.add(it)
            }
        }


        if (foundToys.isEmpty()) {   //перевіряємо чи знайшли іграшки
            Toast.makeText(this@RoomActivity, "Nothing found", Toast.LENGTH_SHORT).show()
        } else {
            toyAdapter.add(foundToys)
        }
    }

    override fun onBackPressed() {
        Controller.clearData()
        super.onBackPressed()
    }
}