package com.example.annonces_immobillieres

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arlib.floatingsearchview.FloatingSearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.login_dialog.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val announces = listOf(
        Announce(
            "Villa",
            "Alger",
            "villa top",
            "15-01-2019",
            "3.100000",
            "4.200000",
            "abderrahmane",
            "0551466583",
            "fa_lebdiri@esi.dz",
            R.drawable.dar1
        ),
        Announce(
            "appartement",
            "Alger",
            "villa top",
            "15-01-2019",
            "3.100000",
            "4.200000",
            "abderrahmane",
            "0551466583",
            "fa_lebdiri@esi.dz",
            R.drawable.dar2

        ),
        Announce(
            "Tiraase",
            "Alger",
            "villa top",
            "15-01-2019",
            "3.100000",
            "4.200000",
            "abderrahmane",
            "0551466583",
            "fa_lebdiri@esi.dz",
            R.drawable.dar3
        ),
        Announce(
            "Villa",
            "Alger",
            "villa top",
            "15-01-2019",
            "3.100000",
            "4.200000",
            "abderrahmane",
            "0551466583",
            "fa_lebdiri@esi.dz",
            R.drawable.dar4
        ),
        Announce(
            "Villa",
            "Alger",
            "villa top",
            "15-01-2019",
            "3.100000",
            "4.200000",
            "abderrahmane",
            "0551466583",
            "fa_lebdiri@esi.dz",
            R.drawable.dar5
        ),
        Announce(
            "Villa",
            "Alger",
            "villa top",
            "15-01-2019",
            "3.100000",
            "4.200000",
            "abderrahmane",
            "0551466583",
            "fa_lebdiri@esi.dz",
            R.drawable.dar6
        )

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Loads animals into the ArrayList

        // Creates a vertical Layout Manager
        rv_animal_list.layoutManager = LinearLayoutManager(this)
        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)
        // Access the RecyclerView Adapter and load the data into it
        rv_animal_list.adapter = ListAdapter(announces)

        rv_animal_list.addOnItemTouchListener(
            RecyclerItemClickListenr(
                this,
                rv_animal_list,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        announces.get(position)
                        //do your work here..
                        val intent = Intent(view.context, DetailsActivity::class.java)
                        intent.putExtra("type", announces.get(position).type)
                        intent.putExtra("wilaya", announces.get(position).wilaya)
                        intent.putExtra("description", announces.get(position).description)
                        intent.putExtra("coordone_X", announces.get(position).coordone_Y)
                        intent.putExtra("coordone_Y", announces.get(position).coordone_X)
                        intent.putExtra("username", announces.get(position).username)
                        intent.putExtra("mobile", announces.get(position).mobile)
                        intent.putExtra("email", announces.get(position).email)

                        startActivity(intent)

                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        TODO("do nothing")
                    }
                })
        )


        fab.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("keyIdentifier", "")
            startActivity(intent)

//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

//        val toggle = ActionBarDrawerToggle(
//            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
//        )
//        drawer_layout.addDrawerListener(toggle)
//        toggle.syncState()

//        floating_search_view.setOnLeftMenuClickListener(
//            new FloatingSearchView.OnLeftMenuClickListener() { ...} );


        floating_search_view.attachNavigationDrawerToMenuButton(drawer_layout);


        nav_view.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        super.onBackPressed()

//        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {

                //Inflate the dialog with custom view
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.login_dialog, null)
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Login Form")
                //show dialog
                val mAlertDialog = mBuilder.show()
                //login button click of custom layout
                mDialogView.dialogLoginBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    //get text from EditTexts of custom layout
                    val name = mDialogView.dialogNameEt.text.toString()
                    val email = mDialogView.dialogEmailEt.text.toString()
                    val password = mDialogView.dialogPasswEt.text.toString()
                    //set the input text in TextView
                }
                //cancel button click of custom layout
                mDialogView.dialogCancelBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
        }

//        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
