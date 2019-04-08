package com.sadogan.androidhw0023

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_results.*


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val nameStr = intent.getStringExtra("name")
        val surnameStr = intent.getStringExtra("surname")
        val tcidStr = intent.getStringExtra("tcid")
        val birthdate = intent.getStringExtra("birthdate")
        val birthplaceStr = intent.getStringExtra("birthplace")
        val photoPathStr = intent.getStringExtra("photo_path")

        result_name.text = "Name: $nameStr"
        result_surname.text = "Surname: $surnameStr"
        result_tcid.text = "TC ID: $tcidStr"
        result_birthdate.text = "Birthdate: $birthdate"
        result_birthplace.text = "Birthplace: $birthplaceStr"

        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeFile(photoPathStr, options)
        result_photo_view.setImageBitmap(bitmap)

        back_button.setOnClickListener {
            finish()
        }

        courses_button.setOnClickListener {
            val intent = Intent(this, RecyclerActivity::class.java)
            startActivity(intent)
        }

    }


}
