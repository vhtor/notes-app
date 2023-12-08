package br.ufrn.imd.mobile.notesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.ufrn.imd.mobile.notesapp.R
import br.ufrn.imd.mobile.notesapp.database.NotasDatabaseHelper
import br.ufrn.imd.mobile.notesapp.databinding.ActivityAtualizarNotaBinding
import br.ufrn.imd.mobile.notesapp.domain.Nota
import br.ufrn.imd.mobile.notesapp.domain.Prioridade

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
            Prioridade.BAIXA -> binding.atualizarBaixaRadioButton.isChecked = true
            Prioridade.MEDIA -> binding.atualizarMediaRadioButton.isChecked = true
            Prioridade.ALTA -> binding.atualizarAltaRadioButton.isChecked = true
            else -> binding.atualizarMediaRadioButton.isChecked = true
        }

        binding.botaoSalvarNota.setOnClickListener {
            val titulo = binding.atualizarTituloNotaInput.text.toString()
            val descricao = binding.atualizarDescricaoNotaInput.text.toString()
            val concluida = binding.atualizarCheckBoxConcluida.isChecked

            val prioridadeRadioButtonId = binding.atualizarPrioridadeRadioGroup.checkedRadioButtonId
            val prioridade = when (prioridadeRadioButtonId) {
                R.id.atualizar_baixaRadioButton -> Prioridade.BAIXA
                R.id.atualizar_mediaRadioButton -> Prioridade.MEDIA
                R.id.atualizar_altaRadioButton -> Prioridade.ALTA
                else -> Prioridade.MEDIA
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
