package com.example.gameroom.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameroom.controller.Controller
import com.example.gameroom.databinding.ActivityMainMenuBinding
import com.example.gameroom.db.MyDbManager
import com.example.gameroom.db.MyDbNameClass
import com.example.gameroom.model.Toy
import com.example.gameroom.room.RoomActivity


class MainMenuActivity : AppCompatActivity(), RoomAdapter.onClick {

    lateinit var bindingClass: ActivityMainMenuBinding
    var myDbManager: MyDbManager? = null
    private var launcher: ActivityResultLauncher<Intent>? = null
    var backPressedTime = 0L    //зберігає час після нажаття кнопки "назад"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        myDbManager = MyDbManager(this)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        myDbManager?.openDb()   //відкриваємо базу даних
        bindingClass.edAmountOfMoney.setTransformationMethod(null)

    }

    override fun startActivity() {
        var intent = Intent(this@MainMenuActivity, RoomActivity::class.java)
        launcher?.launch(intent)

    }

    fun onClickCreateRoom(view: View?) = with(bindingClass) {

        buttonCreateRoom.visibility = View.GONE

        textNewRoom.visibility = View.VISIBLE
        butBack.visibility = View.VISIBLE
        textRoomCreator.visibility = View.VISIBLE

        butBack.setOnClickListener {
            //ховаємо ввід
            textRoomCreator.visibility = View.GONE
            textNewRoom.visibility = View.GONE
            butBack.visibility = View.GONE

            //показуємо головні кнопки
            buttonCreateRoom.visibility = View.VISIBLE

        }
    }

    fun onClickLoadRoom(view: View?) = with(bindingClass) {
        val roomAdapter = RoomAdapter(myDbManager!!, this@MainMenuActivity)

        //ховаємо головні кнопки
        buttonCreateRoom.visibility = View.GONE
        //показуємо список і кнопку назад
        textLoadRoomMenu.visibility = View.VISIBLE
        textChoose.visibility = View.VISIBLE
        butBack.visibility = View.VISIBLE

        butBack.setOnClickListener {
            textChoose.visibility = View.GONE
            butBack.visibility = View.GONE
            textLoadRoomMenu.visibility = View.GONE
            //показуємо головні кнопки
            buttonCreateRoom.visibility = View.VISIBLE

        }
        //створюємо recyclerview
        rcViewLoadRoom.layoutManager = LinearLayoutManager(this@MainMenuActivity)
        rcViewLoadRoom.adapter = roomAdapter
        roomAdapter.update()
    }

    fun onClickOk(view: View?) = with(bindingClass) {

        //перевіримо чи таке ім'я кімнати існує
        if (edNameOfTheRoom.text.toString().equals("")) { //коли нічого не ввели
            tError.text = "Enter the name of the room!"
            return@with
        } else {
            val cursor = myDbManager?.db!!.query(
                MyDbNameClass.TABLE_NAME_ALL_ROOMS,
                null,
                null,
                null,
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                var edName = edNameOfTheRoom.text.toString()
                //якщо знаходимо одинакову назву то виводимо помилку
                if (edName?.equals(cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_NAME_OF_THE_TOY)))) {
                    tError.text = "Such a room name already exists!"
                    return
                }
            }
            cursor.close()
        }

        // далі перевірка на ввід грошей
        if (edAmountOfMoney.text.toString().equals("")) {
            tError.text = "Enter the amount of money!"
        } else if (edAmountOfMoney.text.toString().toDouble() < 0) { //від'ємне число
            tError.text = "Incorrect amount of money!"
        } else {
            //коли здійснено правильний ввід зберігаємо дані в контроллер
            Controller.setName(edNameOfTheRoom.text.toString())
            Controller.setMoney(edAmountOfMoney.text.toString().toDouble())

            //зберігаємо айді кімнати
            var idRoom: Int = myDbManager?.insertNewRoomToDb(
                Controller.getNameOfTheRoom(),
                Controller.getMoney()
            )!!

            Controller.setIdRoom(idRoom)

            //повертаємо в початковий стан
            buttonCreateRoom.visibility = View.VISIBLE
            textNewRoom.visibility = View.GONE
            textRoomCreator.visibility = View.GONE

            //тестові дані
            var toy1: Toy = Toy("Batman", 0.2, "black", "small", 100.0)
            var toy2: Toy = Toy("Superman", 0.15, "red", "small", 120.0)
            var toy3: Toy = Toy("Venom", 0.3, "black", "middle", 200.0)
            var toy4: Toy = Toy("Spiderman", 0.25, "red", "small", 150.0)

            Controller.getToysInTheToyStore().add(toy1)
            Controller.getToysInTheToyStore().add(toy2)
            Controller.getToysInTheRoom().add(toy3)
            Controller.getToysInTheRoom().add(toy4)
            //тестові дані кінець


            //створюємо новий інтент і відкриваємо вікно кімнати
            var intent = Intent(this@MainMenuActivity, RoomActivity::class.java)
            launcher?.launch(intent)

        }
    }

    override fun onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()

        }
        backPressedTime = System.currentTimeMillis()
    }

}

