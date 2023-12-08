package br.ufrn.imd.mobile.notesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.ufrn.imd.mobile.notesapp.database.NotasDatabaseHelper
import br.ufrn.imd.mobile.notesapp.databinding.ActivityDetalhesNotaBinding

class DetalhesNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesNotaBinding
    private lateinit var db: NotasDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotasDatabaseHelper(this)

        val notaId = intent.getIntExtra("nota_id", -1)
        if (notaId == -1) {
            finish()
            return
        }

        val nota = db.getNotaById(notaId)
        binding.tituloDetalhesTextView.text = nota?.titulo
        binding.descricaoDetalhesTextView.text = nota?.descricao
    }
}