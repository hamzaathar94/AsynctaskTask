package com.example.asynctasktask

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asynctasktask.adapter.DataAdapter
import com.example.asynctasktask.databinding.ActivityMainBinding
import com.example.asynctasktask.model.DataModelItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    private  var recyclerView: RecyclerView?= null
    private var dataModel: List<DataModelItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        recyclerView=binding?.recycler

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                Array(1){android.Manifest.permission.READ_EXTERNAL_STORAGE},121
            )
        }

        binding?.button?.setOnClickListener { loadFile() }

        binding?.button2?.setOnClickListener {loadFileCoro()  }

        binding?.button3?.setOnClickListener {  }




    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==121 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            loadFile()
    }

    private fun loadFile():ArrayList<Images>{
        val imag= mutableListOf<Images>()
        val projection = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA)
        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val selcetionArgs = arrayOf("yalla")
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} "

        val cursor =contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selcetionArgs,
            sortOrder
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val image = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("ooo",title)
                imag.add(MediaStore.Images())


            } while (cursor.moveToNext())
            cursor.close()
        }
        return ArrayList(imag)
    }

    private fun loadFileCoro() {
        CoroutineScope(Dispatchers.Main).launch {

            val imag = mutableListOf<Images>()
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA
            )
            val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
            val selcetionArgs = arrayOf("yalla")
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} "

            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selcetionArgs,
                sortOrder
            )
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val title =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    val image =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    Log.d("ooo", image)
                    imag.add(MediaStore.Images())


                } while (cursor.moveToNext())
                cursor.close()
            }
            return@launch
        }

    }
}