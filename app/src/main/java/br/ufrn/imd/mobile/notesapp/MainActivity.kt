package br.ufrn.imd.mobile.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.ufrn.imd.mobile.notesapp.activity.AdicionarNotaActivity
import br.ufrn.imd.mobile.notesapp.adapter.NotasAdapter
import br.ufrn.imd.mobile.notesapp.database.NotasDatabaseHelper
import br.ufrn.imd.mobile.notesapp.databinding.ActivityMainBinding
import br.ufrn.imd.mobile.notesapp.domain.Nota

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotasDatabaseHelper
    private lateinit var notasAdapter: NotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotasDatabaseHelper(this)
        val notas = carregarNotasOrdenadas()
        notasAdapter = NotasAdapter(notas, this)

        binding.notasRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notasRecyclerView.adapter = notasAdapter

        binding.adicionarNotaFloatingButton.setOnClickListener {
            val intent = Intent(this, AdicionarNotaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun carregarNotasOrdenadas(): List<Nota> {
        val notas = db.getAllNotas()
        val notasConcluidas = notas.filter { it.concluida }
        val notasNaoConcluidas = notas.filterNot { it.concluida }

        return notasNaoConcluidas + notasConcluidas
    }

    override fun onResume() {
        super.onResume()
        val notasOrdenadas = carregarNotasOrdenadas()
        notasAdapter.refreshData(notasOrdenadas)
    }
}