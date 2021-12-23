package com.example.gameroom.toyStore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameroom.toyCreator.CreateToyActivity
import com.example.gameroom.controller.Controller
import com.example.gameroom.databinding.ActivityToyStoreBinding
import com.example.gameroom.sort.SortByCost
import java.util.*

class ToyStoreActivity : AppCompatActivity(), ToyStoreAdapter.onClickBuy {

    lateinit var binding: ActivityToyStoreBinding
    private val toyStoreAdapter = ToyStoreAdapter(this)
    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityToyStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

        binding.textCurMoney.text = "${Controller.getMoney()}"

        init()
    }

    override fun onResume() {
        super.onResume()
        //toyStoreAdapter.notifyDataSetChanged()  //оновляємо список після виходу з вікна створення іграшок
        toyStoreAdapter.update()

    }


    fun onClickCreate(view: View) {
        //створюємо новий інтент і відкриваємо вікно створення іграшок
        var intent = Intent(this, CreateToyActivity::class.java)
        launcher?.launch(intent)
    }

    fun onClickBack(view: View) {
        finish()
    }


    override fun changeMoney(money: Double) {

        binding.textCurMoney.text = "${money}"
    }

    override fun notEnoughMoney() {
        Toast.makeText(this, "Not enough money!!!", Toast.LENGTH_SHORT).show()
    }

    private fun init() = with(binding) {

        rcViewStore.layoutManager = LinearLayoutManager(this@ToyStoreActivity)
        rcViewStore.adapter = toyStoreAdapter
        toyStoreAdapter.update()

    }

    fun onClickSort(view: View) {

        if (Controller.getToysInTheToyStore().isEmpty()) {

            Toast.makeText(this, "ToyStore is empty!!!", Toast.LENGTH_LONG).show()

        } else {

            var costComparator = SortByCost()
            Collections.sort(Controller.getToysInTheToyStore(), costComparator)
            Toast.makeText(this, "Toys are successfully sorted by price", Toast.LENGTH_LONG).show()
            toyStoreAdapter.update()
        }

    }

}