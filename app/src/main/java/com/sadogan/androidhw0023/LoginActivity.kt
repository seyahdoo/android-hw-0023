package com.sadogan.androidhw0023

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter

import android.widget.AdapterView
import android.widget.Spinner

import kotlinx.android.synthetic.main.activity_login.*
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.DefaultCallback
import android.content.Intent
import java.io.File
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.util.*


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {


    var selectedCity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        save_button.setOnClickListener { attemptSave() }

        val spinner: Spinner = findViewById(R.id.cities_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.cities_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var v : Any = parent!!.getItemAtPosition(position)
                selectedCity = v as String
                name.error = selectedCity
                name.requestFocus()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        selectedCity = resources.getStringArray(R.array.cities_array)[0]

        photo_button.setOnClickListener {
            EasyImage.openChooserWithGallery( this, "Choose an image", 0)
        }

        birthdate.maxDate = Calendar.getInstance().timeInMillis

    }


    private fun attemptSave() {

        // Reset errors.
        name.error = null
        surname.error = null
        tcid.error = null


        val nameStr = name.text.toString()
        val surnameStr = surname.text.toString()
        val tcidStr = tcid.text.toString()
        val birthdateStr = "${birthdate.dayOfMonth} / ${birthdate.month + 1} / ${birthdate.year}"


        if(TextUtils.isEmpty(nameStr)){
            name.error = getString(R.string.error_field_required)
            name.requestFocus()
            return
        }

        if (containsDigit(nameStr)) {
            name.error = getString(R.string.error_name_cannot_contain_digits)
            name.requestFocus()
            return
        }

        if(TextUtils.isEmpty(surnameStr)){
            surname.error = getString(R.string.error_field_required)
            surname.requestFocus()
            return
        }

        if (containsDigit(surnameStr)) {
            surname.error = getString(R.string.error_name_cannot_contain_digits)
            surname.requestFocus()
            return
        }

        if(TextUtils.isEmpty(tcidStr)){
            tcid.error = getString(R.string.error_field_required)
            tcid.requestFocus()
            return
        }

        if(!TextUtils.isDigitsOnly(tcidStr)){
            tcid.error = getString(R.string.error_must_only_contain_digis)
            tcid.requestFocus()
            return
        }

        if(tcidStr.length != 11){
            tcid.error = getString(R.string.error_must_be_11_in_lengt)
            tcid.requestFocus()
            return
        }


        val myIntent = Intent(this, ResultActivity::class.java)
        myIntent.putExtra("name", nameStr)
        myIntent.putExtra("surname", surnameStr)
        myIntent.putExtra("tcid", tcidStr)
        myIntent.putExtra("birthdate", birthdateStr)
        myIntent.putExtra("birthplace", selectedCity)
        myIntent.putExtra("photo_path", photoPath)

        this.startActivity(myIntent)

    }

    private fun containsDigit(s: String?): Boolean {
        var containsDigit = false

        if (s != null && !s.isEmpty()) {
            for (c in s.toCharArray()) {
                if (Character.isDigit(c)) {
                    containsDigit = true
                    break
                }
            }
        }

        return containsDigit
    }

    var photoPath : String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                photoPath = imageFile?.path
                val bitmap = BitmapFactory.decodeFile(imageFile?.path, options)
                photo_view.setImageBitmap(bitmap)
            }

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

        })
    }


}
