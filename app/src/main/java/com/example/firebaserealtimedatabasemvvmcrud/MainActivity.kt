package com.example.firebaserealtimedatabasemvvmcrud

import android.app.AlertDialog
import android.os.Binder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaserealtimedatabasemvvmcrud.adapter.DataAdapter
import com.example.firebaserealtimedatabasemvvmcrud.databinding.ActivityMainBinding
import com.example.firebaserealtimedatabasemvvmcrud.model.Data
import com.example.firebaserealtimedatabasemvvmcrud.viewmodel.DataViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataViewModel: DataViewModel by viewModels()
    private lateinit var adapter: DataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = DataAdapter(listOf(), this)
        binding.recylerView.adapter = adapter
        binding.recylerView.layoutManager = LinearLayoutManager(this)
        dataViewModel.dataList.observe(this, Observer {

            if (it != null) {
                adapter.updateData(it)
            } else {
                Toast.makeText(this@MainActivity, "error fetching data", Toast.LENGTH_SHORT).show()

            }
        })
        dataViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()

            }
        })
        binding.saveBtn.setOnClickListener {
            val studid = binding.idEt.text.toString()
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val subject = binding.subjectEt.text.toString()
            val code = binding.codeEt.text.toString()

            if (studid.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && code.isNotEmpty()) {
                val data = Data(null, studid, name, email, subject, code)

                dataViewModel.addData( data ,
                    onSuccess = {
                        clearInputFields()
                        Toast.makeText(
                            this@MainActivity,
                            "data save successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, onFailure = {
                        Toast.makeText(this@MainActivity, "failed to add data", Toast.LENGTH_SHORT)
                            .show()
                    })


            }

        }


    }

    private fun clearInputFields() {
        binding.idEt.text?.clear()
        binding.nameEt.text?.clear()
        binding.emailEt.text?.clear()
        binding.subjectEt.text?.clear()
        binding.codeEt.text?.clear()
    }


    fun onEditItemClick(data: Data) {

       binding.idEt.setText(data.studid)
        binding.nameEt.setText(data.name)
        binding.emailEt.setText(data.email)
        binding.subjectEt.setText(data.subject)
        binding.codeEt.setText(data.code)

        binding.saveBtn.setOnClickListener{

            val updatedData = Data(
                data.id,
                binding.idEt.text.toString(),
                binding.nameEt.text.toString(),
                binding.emailEt.text.toString(),
                binding.subjectEt.text.toString(),
                binding.codeEt.text.toString())
              dataViewModel.updateData(updatedData)
            clearInputFields()
            Toast.makeText(this@MainActivity, "data updated successfully", Toast.LENGTH_SHORT).show()

        }
    }

    fun onDeleteItemClick(data: Data) {

        AlertDialog.Builder(this).apply {
            setTitle("Delete Confirmation")
            setMessage("are you sure you want to delete this data? ")
            setPositiveButton("Yes"){_,_  ->
                dataViewModel.deleteData(data,
                    onSuccess = { Toast.makeText(this@MainActivity, "data deleted successfully", Toast.LENGTH_SHORT).show()},
                    onFailure = { Toast.makeText(this@MainActivity, "failed to delete data", Toast.LENGTH_SHORT).show()}
                    )
            }
            setNegativeButton("No",null)
        }.show()
    }

}