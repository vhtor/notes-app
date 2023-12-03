package br.ufrn.imd.mobile.notesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.ufrn.imd.mobile.notesapp.R
import br.ufrn.imd.mobile.notesapp.database.NotasDatabaseHelper
import br.ufrn.imd.mobile.notesapp.databinding.ActivityAdicionarNotaBinding
import br.ufrn.imd.mobile.notesapp.domain.Nota

class AdicionarNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdicionarNotaBinding
    private lateinit var db: NotasDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotasDatabaseHelper(this)

        binding.botaoSalvarNota.setOnClickListener {
            val titulo = binding.tituloNotaInput.text.toString()
            val descricao = binding.descricaoNotaInput.text.toString()
            val nota = Nota(0, titulo, descricao)
            db.inserirNota(nota)
            finish()

            Toast.makeText(this, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
}