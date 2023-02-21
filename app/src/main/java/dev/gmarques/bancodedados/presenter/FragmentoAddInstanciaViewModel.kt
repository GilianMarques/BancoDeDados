package dev.gmarques.bancodedados.presenter

import android.view.View
import android.view.View.GONE
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import dev.gmarques.bancodedados.data.repositorios.InstanciaRepo
import dev.gmarques.bancodedados.data.repositorios.PropriedadeRepo
import dev.gmarques.bancodedados.databinding.InstanciaCampoBooleanoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoNumericoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoTextoBinding
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class FragmentoAddInstanciaViewModel : ViewModel() {
    lateinit var template: Template

    fun validarEntradas(views: ArrayList<ViewBinding>): Boolean {
        views.forEach {
            when (it) {
                is InstanciaCampoNumericoBinding -> if (!validarCampoNumerico(it)) return false
                is InstanciaCampoTextoBinding -> if (!validarCampoDeTexto(it)) return false
            }
        }

        return true
    }

    private fun validarCampoDeTexto(it: InstanciaCampoTextoBinding): Boolean {
        it.tvRegras.visibility = GONE

        val campo = it.root.tag as Campo
        val entradaUsuario = it.edtEntrada.text.toString()

        return campo.validarEntradaDeTexto(entradaUsuario)
            .apply {
                if (!this) {
                    it.tvRegras.visibility = View.VISIBLE
                    it.edtEntrada.requestFocus()
                }
            }
    }

    private fun validarCampoNumerico(it: InstanciaCampoNumericoBinding): Boolean {
        it.tvRegras.visibility = GONE

        val campo = it.root.tag as Campo
        val entradaUsuario = it.edtEntrada.text.toString().toLongOrNull()

        return campo.validarEntradaNumerica(entradaUsuario)
            .apply {
                if (!this) {
                    it.tvRegras.visibility = View.VISIBLE
                    it.edtEntrada.requestFocus()
                }
            }
    }

    suspend fun salvarObjeto(views: ArrayList<ViewBinding>) = withContext(IO) {

        val instancia = Instancia(template.uid)
        InstanciaRepo.addInstancia(instancia)

        views.forEach { viewBinding ->

            val campo = viewBinding.root.tag as Campo
            val propriedade = Propriedade(instancia.uid, campo.tipoCampo)
            atribuiValor(campo, propriedade, viewBinding)

            PropriedadeRepo.addPropriedade(propriedade)
        }
    }

    private fun atribuiValor(campo: Campo, propriedade: Propriedade, viewBinding: ViewBinding) {
        when (campo.tipoCampo) {

            TipoCampo.NUMERO -> {
                propriedade.valorDouble =
                    (viewBinding as InstanciaCampoNumericoBinding).edtEntrada.text.toString()
                        .toDoubleOrNull() ?: 0.0
            }

            TipoCampo.TEXTO -> {
                propriedade.valorString =
                    (viewBinding as InstanciaCampoTextoBinding).edtEntrada.text.toString()
            }

            TipoCampo.REAL -> {
                propriedade.valorBoolean =
                    (viewBinding as InstanciaCampoBooleanoBinding).swEntrada.isChecked
            }
        }
    }

}
