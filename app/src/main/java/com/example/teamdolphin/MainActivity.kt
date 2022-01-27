package com.example.teamdolphin

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

//This is the Home Page
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This is a click listener for the button to navigate to FileCreation Page
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, FileCreation::class.java)
            startActivity(intent)
        }

        var projectList = ArrayList<ProjectFile>()

        //Currently these are dummy objects that you can see in the homepage
        var names = arrayOf(
            "image1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "image2", "image3","image1", "image2", "image3","image1", "image2", "image3"
        )

        var images = intArrayOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
        )

        //Loop that goes through names or images and adds images & name to projectList
        for(i in names.indices){
            projectList.add(ProjectFile(names[i], images[i]))
        }


        var customAdapter = CustomAdapter(projectList, this)

        //This gridView exists in activity_main.xml
        var gridView = findViewById<GridView>(R.id.homepage_gridView)

        gridView.adapter= customAdapter
    }


    //Adapter that helps inflate a view
    class CustomAdapter(
        var viewProject: ArrayList<ProjectFile>,
        var context: Context
    ) : BaseAdapter(){

        var layoutInflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return viewProject.size
        }

        override fun getItem(p0: Int): Any {
            return viewProject[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var view = p1
            if(view==null){
                view = layoutInflator.inflate(R.layout.item, p2, false)
            }
            var imageName = view?.findViewById<TextView>(R.id.item_image_name)
            var imageView = view?.findViewById<ImageView>(R.id.item_image)

            imageName?.text = viewProject[p0].name
            imageView?.setImageResource(viewProject[p0].image!!)

            return  view!!
        }

    }
}