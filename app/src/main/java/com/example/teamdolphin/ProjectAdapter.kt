package com.example.teamdolphin

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class ProjectAdapter(
    private val ctx: Context,
    private val filesNames: Array<String>,
    private val filesPaths: Array<String>
) :
    BaseAdapter() {

    var layoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        //println("Everything good until here1")
        return filesPaths.size
    }

    override fun getItem(pos: Int): Int {
        //println("Everything good until here2")
        return pos
    }

    override fun getItemId(pos: Int): Long {
        //println("Everything good until here3")
        return pos.toLong()
    }
    
    override fun getView(p: Int, convertView: View?, parent: ViewGroup): View? {
        //println("Everything good until here4")
        var grid: View? = convertView
        if (convertView == null) {
            //println("Everything good until here5")
            grid = layoutInflater.inflate(R.layout.gridview_item, parent, false)
            var textView = grid?.findViewById<TextView>(R.id.gridview_text)
            var imageView = grid?.findViewById<ImageView>(R.id.gridview_image)
            //println("Everything good until here6")
            textView?.text = filesNames[p].substring(0, filesNames[p].lastIndexOf('.'))
            val bmp = BitmapFactory.decodeFile(filesPaths[p])
            imageView?.setImageBitmap(bmp)
            //println("Everything good until here7")
        } else {
        }
        return grid
    }
}