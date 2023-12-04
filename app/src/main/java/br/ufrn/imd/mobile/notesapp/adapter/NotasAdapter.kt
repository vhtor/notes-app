package br.ufrn.imd.mobile.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.ufrn.imd.mobile.notesapp.R
import br.ufrn.imd.mobile.notesapp.domain.Nota

class NotasAdapter(private var notas: List<Nota>, context: Context) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)
        val descricaoTextView: TextView = itemView.findViewById(R.id.descricaoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nota_item, parent, false)
        return NotaViewHolder(view)
    }

    override fun getItemCount(): Int = notas.size

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.tituloTextView.text = nota.titulo
        holder.descricaoTextView.text = nota.descricao
    }

    fun refreshData(novasNotas: List<Nota>) {
        notas = novasNotas
        notifyDataSetChanged()
    }
}