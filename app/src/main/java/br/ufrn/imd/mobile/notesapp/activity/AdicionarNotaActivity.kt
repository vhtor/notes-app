package br.ufrn.imd.mobile.notesapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.ufrn.imd.mobile.notesapp.R
import br.ufrn.imd.mobile.notesapp.database.NotasDatabaseHelper
import br.ufrn.imd.mobile.notesapp.databinding.ActivityAdicionarNotaBinding
import br.ufrn.imd.mobile.notesapp.domain.Nota
import br.ufrn.imd.mobile.notesapp.domain.Prioridade

class AdicionarNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdicionarNotaBinding
    private lateinit var db: NotasDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotasDatabaseHelper(this)

        binding.checkBoxConcluida.setOnCheckedChangeListener { _, isChecked ->
            isChecked.let {
                binding.checkBoxConcluida.isChecked = it
            }
        }

        binding.botaoSalvarNota.setOnClickListener {
            val titulo = binding.tituloNotaInput.text.toString()
            val descricao = binding.descricaoNotaInput.text.toString()
            val concluida = binding.checkBoxConcluida.isChecked

            val prioridade = when (binding.prioridadeRadioGroup.checkedRadioButtonId) {
                R.id.baixaRadioButton -> Prioridade.BAIXA
                R.id.mediaRadioButton -> Prioridade.MEDIA
                R.id.altaRadioButton -> Prioridade.ALTA
                else -> Prioridade.MEDIA
            }

            if (titulo.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nota = Nota(0, titulo, descricao, concluida, prioridade)
            db.inserirNota(nota)
            finish()

            Toast.makeText(this, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
}