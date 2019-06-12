package com.example.annonces_immobillieres


import android.Manifest
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_details.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.view.View


class DetailsActivity : AppCompatActivity() {

    internal lateinit var viewpager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        val type: String = intent.getStringExtra("type")
        val wilaya: String = intent.getStringExtra("wilaya")
        val description: String = intent.getStringExtra("description")
        val coordone_X: String = intent.getStringExtra("coordone_X")
        val coordone_Y: String = intent.getStringExtra("coordone_Y")
        val username: String = intent.getStringExtra("username")
        val mobile: String = intent.getStringExtra("mobile")
        val email: String = intent.getStringExtra("email")

        setTitle(type + " - " + wilaya)

        txt_type?.text = "Type : " + type
        txt_wilaya?.text = "Wilaya : " + wilaya
        txt_desc?.text = "Description : " + description
        txt_username?.text = "Username : " + username
        txt_mobile?.text = "Mobile : " + mobile
        txt_mail?.text = "Mail : " + email

        btn_appeler.setOnClickListener { view ->
            //            val intent = Intent(Intent.ACTION_CALL);
//            intent.data = Uri.parse("tel:$mobile")
//            startActivity(intent)
            buChargeEvent(view, mobile);
        }

        viewpager = findViewById(R.id.viewpager) as ViewPager

        val adapter = ViewPagerAdapter(this)
        viewpager.adapter = adapter

    }

    fun buChargeEvent(view: View, number: String) {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this as Activity, Manifest.permission.CALL_PHONE
                )
            ) {

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE), 555
                )
            }
        } else {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$number")
            startActivity(callIntent)

        }
    }
}
