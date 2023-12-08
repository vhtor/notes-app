package br.ufrn.imd.mobile.notesapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.ufrn.imd.mobile.notesapp.domain.Nota
import br.ufrn.imd.mobile.notesapp.domain.Prioridade

class NotasDatabaseHelper(context: Context) : SQLiteOpenHelper(context, BANCO_NOME, null, BANCO_VERSAO) {
    companion object {
        private const val BANCO_NOME = "notas.db"
        private const val TABELA_NOME = "notas"
        private const val COLUNA_ID = "id"
        private const val COLUNA_TITULO = "titulo"
        private const val COLUNA_DESCRICAO = "descricao"
        private const val COLUNA_CONCLUIDA = "concluida"
        private const val COLUNA_PRIORIDADE = "prioridade"
        private const val BANCO_VERSAO = 3
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABELA_NOME (" +
                "$COLUNA_ID INTEGER PRIMARY KEY, " +
                "$COLUNA_TITULO TEXT, " +
                "$COLUNA_DESCRICAO TEXT, " +
                "$COLUNA_CONCLUIDA INTEGER, " +
                "$COLUNA_PRIORIDADE INTEGER)"

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
            put(COLUNA_CONCLUIDA, if (nota.concluida) 1 else 0)
            put(COLUNA_PRIORIDADE, nota.prioridade.ordinal)
        }
        db.insert(TABELA_NOME, null, valores)
        db.close()
    }

    fun atualizarNota(nota: Nota) {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUNA_TITULO, nota.titulo)
            put(COLUNA_DESCRICAO, nota.descricao)
            put(COLUNA_CONCLUIDA, if (nota.concluida) 1 else 0)
            put(COLUNA_PRIORIDADE, nota.prioridade.ordinal)
        }
        db.update(TABELA_NOME, valores, "$COLUNA_ID = ?", arrayOf(nota.id.toString()))
        db.close()
    }

    fun getAllNotas(): List<Nota> {
        val notas = mutableListOf<Nota>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABELA_NOME ORDER BY $COLUNA_CONCLUIDA",
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val idIndex = getColumnIndex(COLUNA_ID)
                val tituloIndex = getColumnIndex(COLUNA_TITULO)
                val descricaoIndex = getColumnIndex(COLUNA_DESCRICAO)
                val concluidaIndex = getColumnIndex(COLUNA_CONCLUIDA)
                val prioridadeIndex = getColumnIndex(COLUNA_PRIORIDADE)

                val id = if (idIndex >= 0) getInt(idIndex) else -1
                val titulo = if (tituloIndex >= 0) getString(tituloIndex) else ""
                val descricao = if (descricaoIndex >= 0) getString(descricaoIndex) else ""
                val concluida = if (concluidaIndex >= 0) getInt(concluidaIndex) == 1 else false
                val prioridadeInt = if (prioridadeIndex >= 0) getInt(prioridadeIndex) else -1
                val prioridade = if (prioridadeInt >= 0) Prioridade.entries[prioridadeInt] else Prioridade.MEDIA

                notas.add(Nota(id, titulo, descricao, concluida, prioridade))
            }
        }

        cursor.close()
        db.close()
        return notas
    }

    fun getNotaById(id: Int): Nota? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABELA_NOME WHERE $COLUNA_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToNext()) {
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_TITULO))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DESCRICAO))
            val concluida = cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_CONCLUIDA)) == 1
            val prioridadeInt = cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_PRIORIDADE))
            val prioridade = Prioridade.entries[prioridadeInt]

            val nota = Nota(id, titulo, descricao, concluida, prioridade)
            cursor.close()
            db.close()
            return nota
        }
        cursor.close()
        db.close()
        return null
    }

    fun removerNota(notaId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUNA_ID = ?"
        val whereArgs = arrayOf(notaId.toString())
        db.delete(TABELA_NOME, whereClause, whereArgs)
        db.close()
    }
}