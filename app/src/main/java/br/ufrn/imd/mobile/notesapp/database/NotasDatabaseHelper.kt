package br.ufrn.imd.mobile.notesapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.ufrn.imd.mobile.notesapp.domain.Nota

class NotasDatabaseHelper(context: Context) : SQLiteOpenHelper(context, BANCO_NOME, null, BANCO_VERSAO) {
    companion object {
        private const val BANCO_NOME = "notas.db"
        private const val TABELA_NOME = "notas"
        private const val COLUNA_ID = "id"
        private const val COLUNA_TITULO = "titulo"
        private const val COLUNA_DESCRICAO = "descricao"
        private const val BANCO_VERSAO = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABELA_NOME (" +
                "$COLUNA_ID INTEGER PRIMARY KEY, " +
                "$COLUNA_TITULO TEXT, " +
                "$COLUNA_DESCRICAO TEXT)"

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABELA_NOME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun inserirNota(nota: Nota) {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUNA_TITULO, nota.titulo)
            put(COLUNA_DESCRICAO, nota.descricao)
        }
        db.insert(TABELA_NOME, null, valores)
        db.close()
    }
}