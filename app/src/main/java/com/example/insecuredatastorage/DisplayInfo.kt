package com.example.insecuredatastorage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.insecuredatastorage.databinding.DisplayInfoBinding
import android.widget.Toast

class DisplayInfo : AppCompatActivity() {
    private lateinit var binding: DisplayInfoBinding
    private lateinit var db: DBsample
    private lateinit var adapter: DisplayAdapter
    private var employees: MutableList<Employee> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DisplayInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBsample(this)

        if (db.getAllEmployee().isEmpty()) {
            db.addDefaultEmployees()
        }
        binding.recyclerViewEmployees.layoutManager = LinearLayoutManager(this)
        loadEmployees()
    }

    private fun loadEmployees() {
        employees = db.getAllEmployee().toMutableList()
        adapter = DisplayAdapter(employees, object : DisplayAdapter.OnDeleteClickListener {
            override fun onDeleteClick(employee: Employee) {
                val isDeleted = db.deleteEmployee(employee.id)
                if (isDeleted > 0) {
                    employees.remove(employee)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@DisplayInfo, "Đã xóa nhân viên ${employee.name}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DisplayInfo, "Không thể xóa nhân viên", Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.recyclerViewEmployees.adapter = adapter
    }
}