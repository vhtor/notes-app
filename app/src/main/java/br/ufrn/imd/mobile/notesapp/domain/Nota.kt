package br.ufrn.imd.mobile.notesapp.domain

data class Nota(val id: Int, val titulo: String, val descricao: String, val concluida: Boolean, val prioridade: String)
