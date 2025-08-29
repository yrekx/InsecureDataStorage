package com.example.insecuredatastorage

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Button

data class Employee(
    val id: Int,
    val name: String,
    val email: String,
    val position: String
)

class DisplayAdapter(
    private val employees: List<Employee>,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<DisplayAdapter.EmployeeViewHolder>()
{
    interface OnDeleteClickListener {
        fun onDeleteClick(employee: Employee)
    }

    class EmployeeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewPosition: TextView = itemView.findViewById(R.id.textViewPosition)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_card, parent, false)
        return EmployeeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentEmployee = employees[position]
        holder.textViewName.text = currentEmployee.name
        holder.textViewPosition.text = currentEmployee.position
        holder.textViewEmail.text = currentEmployee.email

        holder.btnDelete.setOnClickListener {
            onDeleteClickListener.onDeleteClick(currentEmployee)
        }
    }

    override fun getItemCount(): Int {
        return employees.size
    }
}
