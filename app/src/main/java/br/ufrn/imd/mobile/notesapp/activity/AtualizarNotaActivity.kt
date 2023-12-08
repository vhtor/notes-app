package br.ufrn.imd.mobile.notesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.ufrn.imd.mobile.notesapp.R
import br.ufrn.imd.mobile.notesapp.database.NotasDatabaseHelper
import br.ufrn.imd.mobile.notesapp.databinding.ActivityAtualizarNotaBinding
import br.ufrn.imd.mobile.notesapp.domain.Nota

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
        binding.atualizarCheckBoxConcluida.isChecked = nota?.concluida ?: false

        when (nota?.prioridade) {
            "Baixa" -> binding.atualizarBaixaRadioButton.isChecked = true
            "Média" -> binding.atualizarMediaRadioButton.isChecked = true
            "Alta" -> binding.atualizarAltaRadioButton.isChecked = true
            else -> {}
        }

        binding.botaoSalvarNota.setOnClickListener {
            val titulo = binding.atualizarTituloNotaInput.text.toString()
            val descricao = binding.atualizarDescricaoNotaInput.text.toString()
            val concluida = binding.atualizarCheckBoxConcluida.isChecked

            val prioridadeRadioButtonId = binding.atualizarPrioridadeRadioGroup.checkedRadioButtonId
            val prioridade = when (prioridadeRadioButtonId) {
                R.id.atualizar_baixaRadioButton -> "Baixa"
                R.id.atualizar_mediaRadioButton -> "Média"
                R.id.atualizar_altaRadioButton -> "Alta"
                else -> "Média"
            }

            if (titulo.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val notaAtualizada = Nota(notaId, titulo, descricao, concluida, prioridade)
            db.atualizarNota(notaAtualizada)

            Toast.makeText(this, "Nota atualizada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
