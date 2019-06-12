package com.example.annonces_immobillieres

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.coord_dialog.view.*
import kotlinx.android.synthetic.main.login_dialog.view.*
import android.R.attr.path
import android.support.design.widget.Snackbar
import android.widget.ImageView
import android.widget.TextView
import java.io.File


class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var list_of_type = arrayOf("Appartement", "Terrain", "Villa", "Local", "Carcasse", "Studio")

    var list_of_wilaya = arrayOf("Alger", "Bejaia", "Tizi Ouzou", "Oran", "Batna")

    var type_selected: String = ""
    var wilaya_selected: String = ""
    var description: String = ""
    var username: String = ""
    var mobile: String = ""
    var mail: String = ""

    private var in_username: TextView? = null
    private var in_mobile: TextView? = null
    private var in_desc: TextView? = null
    private var in_mail: TextView? = null


    var coord_x: String = ""
    var coord_y: String = ""

    private var context: Context? = null
    var PICK_IMAGE_MULTIPLE = 1
    lateinit var imagePath: String
    var imagesPathList: MutableList<String> = arrayListOf()


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.getId() == R.id.spin_type) {
            type_selected = list_of_type.get(position)
            //do this
        } else if (parent?.getId() == R.id.spin_wilaya) {
            wilaya_selected = list_of_wilaya.get(position)
        }

//        textView_msg!!.text = "Selected : "+list_of_type[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        init()
        setTitle("Ajouter une annonce")

        spin_type!!.setOnItemSelectedListener(this)

        spin_wilaya!!.setOnItemSelectedListener(this)

        val array_adapter_type = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_type)
        array_adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val array_adapter_wilaya = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_wilaya)
        array_adapter_wilaya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        spin_type!!.setAdapter(array_adapter_type)
        spin_wilaya!!.setAdapter(array_adapter_wilaya)

        btn_coord.setOnClickListener { view ->

            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.coord_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Coordonés gps")
            //show dialog
            val mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.btn_valider.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                //get text from EditTexts of custom layout
                coord_x = mDialogView.input_x.text.toString()
                coord_y = mDialogView.input_y.text.toString()
                //set the input text in TextView
            }


        }

        btn_photos.setOnClickListener { view ->
            call_intent_photos()
        }


        btn_add.setOnClickListener { view ->
            add_annonce(view)
        }
    }

    private fun init() {
        in_desc = findViewById(R.id.in_desc)
        in_username = findViewById(R.id.in_username)
        in_mail = findViewById(R.id.in_mail)
        in_mobile = findViewById(R.id.in_mobile)

    }

    private fun add_annonce(view: View) {
        description = in_desc?.text.toString()
         username = in_username?.text.toString()
        mail = in_mail?.text.toString()
        mobile = in_mobile?.text.toString()



//        private var item_description: TextView? = null
//        private var item_username: TextView? = null
//        private var item_mobile: TextView? = null
//        private var item_mail: TextView? = null

        Snackbar.make(view, "" +
                "Type:" + type_selected
                + "- Wilaya:" + wilaya_selected
                + "- description:" + description
                + "- username:" + username
                + "- mail:" + mail
                + "- mobile:" + mobile

            , 5000).show()
    }

    fun call_intent_photos() {
        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture")
                , PICK_IMAGE_MULTIPLE
            )
        } else {
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
            && null != data
        ) {
            if (data.getClipData() != null) {
                var count = data.clipData.itemCount
                for (i in 0..count - 1) {

                    var imageUri: Uri = data.clipData.getItemAt(i).uri
                    getPathFromURI(imageUri)

                }
            } else if (data.getData() != null) {
                var imagePath: String = data.data.path
                Log.e("imagePath", imagePath);
            }

            displayImageData()
        }
    }

    private fun getPathFromURI(uri: Uri) {
        var path: String = uri.path // uri = any content Uri

        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (path.contains("/document/image:")) { // files selected from "Documents"
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else { // files selected from all other sources, especially on Samsung devices
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.DATE_TAKEN
            ) // some example data you can query
            val cursor = contentResolver.query(
                databaseUri,
                projection, selection, selectionArgs, null
            )
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(projection[0])
                imagePath = cursor.getString(columnIndex)
                // Log.e("path", imagePath);
                imagesPathList.add(imagePath)
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("TAG", e.message, e)
        }
    }

    fun displayImageData() {

        for (e in imagesPathList) {

            Log.e("imagePath", e);

//            println("The element is $e")
        }
    }
}
