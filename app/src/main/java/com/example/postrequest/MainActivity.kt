package com.example.postrequest

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postrequest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var list: GetItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APIRequest()


    }


    private fun addItem(name: String, location: String) {
        val apiInterface = APIClient().getItem()?.create(APIInterface ::class.java)
        apiInterface?.addItem(PostItem(name,location))?.enqueue(object :Callback<PostItem>{
            override fun onResponse(call: Call<PostItem>, response: Response<PostItem>) {
                list.add(response.body()!!)
                binding.recyclerView.adapter?.notifyDataSetChanged()
                Toast.makeText(applicationContext,"Add successful",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<PostItem>, t: Throwable) {
                Toast.makeText(applicationContext,"something wrong",Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun APIRequest() {
        val apiInterface  = APIClient().getItem()?.create(APIInterface::class.java)
        apiInterface?.getItem()?.enqueue(object :Callback<GetItem>{
            override fun onResponse(call: Call<GetItem>, response: Response<GetItem>) {
                try {

                    list = response.body()!!
                    binding.recyclerView.adapter = RecycleViewAdpter(list)
                    binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

                }catch (e :Exception){
                    Log.e("CATCH", "$e")
                }
            }

            override fun onFailure(call: Call<GetItem>, t: Throwable) {
                Log.e("onFailure Activty", "$t")
            }

        })

    }

    fun postItem(view: View) {
        val dialog = AlertDialog.Builder(this)

        val etName = EditText(this)
        etName.hint = "Name"
        val etLocation = EditText(this)
        etLocation.hint = "Location"
        dialog.setMessage("Add")
            .setPositiveButton("ADD",DialogInterface.OnClickListener { _, _ ->
                addItem(etName.text.toString(), etLocation.text.toString())
            })
            .setNegativeButton("CANCEL",DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.cancel()
            })
        val alert = dialog.create()
        val ly = LinearLayout(this)
        ly.orientation = LinearLayout.VERTICAL;
        ly.addView(etName)
        ly.addView(etLocation)
        alert.setView(ly)
        alert.show()
    }
}