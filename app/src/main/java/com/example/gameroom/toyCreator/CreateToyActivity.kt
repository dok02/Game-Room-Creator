package com.example.gameroom.toyCreator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gameroom.controller.Controller
import com.example.gameroom.databinding.ActivityCreateToyBinding
import com.example.gameroom.db.MyDbManager
import com.example.gameroom.model.Toy

class CreateToyActivity : AppCompatActivity() {

    var myDbManager: MyDbManager? = null
    lateinit var binding:ActivityCreateToyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateToyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickBack(view: View){
        finish()
    }


    fun onClickSave(view: View?) = with(binding) {


        //перевірка на ввід double

        var weight =edWeight.getText().toString().toDoubleOrNull()
        var price = edPrice.getText().toString().toDoubleOrNull()
        var name = edName.text.toString()
        var color = edColor.getText().toString()
        var size = edSize.getText().toString()
        if (weight != null && price != null && !name.equals("") && !color.equals("") && !size.equals("")) {
            if(weight < 0 || price <0){

                textError.setText("Incorrect input!!!")
                return@with
            }
        }else{
            textError.setText("Incorrect input!!!")
            return@with
        }


        var toy: Toy =Toy(name, weight, color, size, price)

        Controller.getToysInTheToyStore().add(toy)
        Toast.makeText(this@CreateToyActivity,"Successfully created",Toast.LENGTH_SHORT).show()
        edName.setText("")
        edWeight.setText("")
        edColor.setText("")
        edSize.setText("")
        edPrice.setText("")
        textError.setText("")
    }

    override fun onDestroy() {
        super.onDestroy()
        //myDbManager?.clodeDb()
    }
}