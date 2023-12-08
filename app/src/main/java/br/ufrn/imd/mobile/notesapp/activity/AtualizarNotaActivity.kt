package br.ufrn.imd.mobile.notesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.ufrn.imd.mobile.notesapp.database.NotasDatabaseHelper
import br.ufrn.imd.mobile.notesapp.databinding.ActivityAtualizarNotaBinding

class AtualizarNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarNotaBinding
    private lateinit var db: NotasDatabaseHelper
    private var notaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotasDatabaseHelper(this)

        notaId = intent.getIntExtra("nota_id", -1)
        if (notaId == -1) {
            finish()
            return
        }

        val nota = db.getNotaById(notaId)
        binding.atualizarTituloNotaInput.setText(nota?.titulo)
        binding.atualizarDescricaoNotaInput.setText(nota?.descricao)

        binding.botaoSalvarNota.setOnClickListener {
            val titulo = binding.atualizarTituloNotaInput.text.toString()
            val descricao = binding.atualizarDescricaoNotaInput.text.toString()

            if (titulo.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val notaAtualizada = nota?.copy(titulo = titulo, descricao = descricao)
            if (notaAtualizada != null) {
                db.atualizarNota(notaAtualizada)
            }

            Toast.makeText(this, "Nota atualizada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}