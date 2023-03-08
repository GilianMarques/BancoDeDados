package dev.gmarques.bancodedados.presenter.fragmento_add_instancia

import android.view.View
import android.view.View.GONE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.lifecycle.HiltViewModel
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
import dev.gmarques.bancodedados.presenter.SingleShotLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FragAddInstanciaViewModel @Inject constructor(
    private val instanciaRepo: InstanciaRepo,
    private val propriedadeRepo: PropriedadeRepo,
) : ViewModel() {

    lateinit var template: Template

    private val _validarESalvar = SingleShotLiveData<Boolean>()
    val validarESalvar: LiveData<Boolean?> get() = _validarESalvar


    fun validareSalvarInstancia(views: ArrayList<ViewBinding>) = viewModelScope.launch(IO) {
        if (validarEntradas(views)) {
            salvarObjeto(views)
            _validarESalvar.postValue(true)
        } else _validarESalvar.postValue(false)
    }

    private fun validarEntradas(views: ArrayList<ViewBinding>): Boolean {
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
// TODO: mover logica que modifica view para o fragmento
        return campo.validarEntradaDeTexto(entradaUsuario)
            .apply {
                if (!this) {
                    it.tvRegras.visibility = View.VISIBLE
                    it.edtEntrada.requestFocus()
                }
            }
    }

    /**
     * @return true se a validação concluiu que a entrada e valida, senao, false
     * */
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

    private suspend fun salvarObjeto(views: ArrayList<ViewBinding>) {

        val instancia = Instancia()
        instanciaRepo.addInstancia(instancia, template)

        views.forEach { viewBinding ->

            val campo = viewBinding.root.tag as Campo
            val propriedade = Propriedade(campo.tipoCampo)
            atribuiValor(campo, propriedade, viewBinding)

            propriedadeRepo.addPropriedade(propriedade, instancia)
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

            TipoCampo.BOOLEANO -> {
                propriedade.valorBoolean =
                        (viewBinding as InstanciaCampoBooleanoBinding).swEntrada.isChecked
            }
        }
    }


}
