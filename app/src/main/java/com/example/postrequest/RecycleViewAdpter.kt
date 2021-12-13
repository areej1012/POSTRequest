package com.example.postrequest

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.postrequest.databinding.ItemRowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RecycleViewAdpter(private var list: ArrayList<PostItem>, var context : Context): RecyclerView.Adapter<RecycleViewAdpter.ItemRowHolder>() {
    class ItemRowHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
    val apiinterface = APIClient().getItem()?.create(APIInterface :: class.java)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        return ItemRowHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        var listItem = list
        holder.binding.apply {
            tvName.text = listItem[position].name
            tvLocation.text = "    ${listItem[position].location}"
        }
        holder.itemView.setOnClickListener {
          showAlert(position)
        }
    }

    private fun showAlert(position: Int) {
        val dialog = AlertDialog.Builder(context)
        val etName = EditText(context)
        etName.setText(list[position].name)
        val etLocation = EditText(context)
        etLocation.setText(list[position].location)
        dialog.setMessage("Update data")
                .setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
                   val updatedate = PostItem(etName.text.toString(), etLocation.text.toString(), list[position].pk)
                    list[position].pk?.let { updateItem(it , updatedate , position) }
                })
                .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialogInterface, _ ->
                    dialogInterface.cancel()
                })
        val alert = dialog.create()
        val ly = LinearLayout(context)
        ly.orientation = LinearLayout.VERTICAL
        ly.addView(etName)
        ly.addView(etLocation)
        alert.setView(ly)
        alert.show()
    }

    override fun getItemCount(): Int = list.size

    fun deleteItem(position: Int){

        list[position].pk?.let { apiinterface?.deleteItem(it)?.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText( context, "Delete successful",Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText( context, "Delete Fail",Toast.LENGTH_LONG).show()
            }

        }) }
    }

    fun updateItem(pk : Int, updateDate : PostItem ,  position: Int){
        apiinterface?.updateItem(pk, updateDate)?.enqueue(object :Callback<PostItem>{
            override fun onResponse(call: Call<PostItem>, response: Response<PostItem>) {
                Toast.makeText( context, "Update successful",Toast.LENGTH_LONG).show()
                list[position] = updateDate
                notifyDataSetChanged()
            }

            override fun onFailure(call: Call<PostItem>, t: Throwable) {
                Toast.makeText( context, "Delete Fail",Toast.LENGTH_LONG).show()
            }

        })
    }
}