package com.example.retobootcamp.utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retobootcamp.R
import com.example.retobootcamp.base.BaseViewHolder
import com.example.retobootcamp.services.model.Document

class RecyclerAdapter(
    val context: Context,
    private val listDocuments: Array<Document>,
    private val listAdjunto: MutableList<String>,
    val itemClickListener : OnItemClick
):RecyclerView.Adapter<RecyclerAdapter.DocumentsViewHolder>() {
    interface OnItemClick{
        fun onClick(adjunto: String?, context: Context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
        return DocumentsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_documents,parent,false))
    }

    override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
                holder.bind(listDocuments[position],listAdjunto[position],position)

    }

    override fun getItemCount(): Int {
        println("tamaño array ${listDocuments.size}")
        return listDocuments.size
    }

    inner class DocumentsViewHolder(itemView: View):BaseViewHolder<Document>(itemView){
        override fun bind(item: Document,adjunto:String,position: Int) {
            val txtName : TextView = itemView.findViewById(R.id.txtNames)
            val txtFechaTipo : TextView = itemView.findViewById(R.id.txtFecha_Tipo)
            txtName.text = "${item.Nombre} ${item.Apellido}"
            txtFechaTipo.text = "${item.Fecha} - ${item.TipoAdjunto}"
            itemView.setOnClickListener {
                    itemClickListener.onClick(adjunto, context)
            }
        }
    }
}