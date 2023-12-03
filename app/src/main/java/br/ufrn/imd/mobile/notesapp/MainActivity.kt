package br.ufrn.imd.mobile.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.ufrn.imd.mobile.notesapp.activity.AdicionarNotaActivity
import br.ufrn.imd.mobile.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adicionarNotaFloatingButton.setOnClickListener {
            val intent = Intent(this, AdicionarNotaActivity::class.java)
            startActivity(intent)
        }
    }
}