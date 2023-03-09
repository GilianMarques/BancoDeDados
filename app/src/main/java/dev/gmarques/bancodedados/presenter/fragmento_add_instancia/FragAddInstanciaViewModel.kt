package dev.gmarques.bancodedados.presenter.fragmento_add_instancia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gmarques.bancodedados.data.repositorios.InstanciaRepo
import dev.gmarques.bancodedados.data.repositorios.PropriedadeRepo
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Template
import dev.gmarques.bancodedados.presenter.SingleShotLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragAddInstanciaViewModel @Inject constructor(
    private val instanciaRepo: InstanciaRepo,
    private val propriedadeRepo: PropriedadeRepo,
) : ViewModel() {

    val instancia = Instancia()
    lateinit var template: Template

    private val _validarESalvar = SingleShotLiveData<Boolean>()
    val validarESalvar: LiveData<Boolean?> get() = _validarESalvar


    fun salvarInstancia() = viewModelScope.launch(IO) {

        instanciaRepo.addInstancia(instancia, template)

        instancia.getPropriedades().forEach { propriedade ->
            propriedadeRepo.addPropriedade(propriedade, instancia)
        }
        _validarESalvar.postValue(true)
    }

    /**
     * @return true se a validação concluiu que a entrada e valida, senao, false
     * */
    fun validarCampoDeTexto(entradaUsuario: String, campo: Campo) =
            campo.validarEntradaDeTexto(entradaUsuario)

    /**
     * @return true se a validação concluiu que a entrada e valida, senao, false
     * */
    fun validarCampoNumerico(entradaUsuario: Double?, campo: Campo): Boolean {
        return campo.validarEntradaNumerica(entradaUsuario)
    }

    /**
     * Essa função é usada para incluir a entrada do usuario na instancia do template no momento
     * da validação do valor que o usuario colocou no campo referente a essa propriedade.
     *
     *  A ideia é validar e registrar o valor na instancia para salvar posteriormente no DB
     * de uma unica vez (dentro do mesmo loop), otimizando a performance.
     *
     * Atenção: Nenhuma validação é feita. Se a validação dos campos falhar todas as propriedades adicionadas
     * a instanicia devem ser removidas.
     * */
    fun addPropriedadeNaInstancia(entradaUsuario: String?, campo: Campo) =
            instancia.addPropriedade(Propriedade(campo.tipoCampo).apply {
                        valorString = entradaUsuario ?: ""
                    })

    /**
     * Essa função é usada para incluir a entrada do usuario na instancia do template no momento
     * da validação do valor que o usuario colocou no campo referente a essa propriedade.
     *
     *  A ideia é validar e registrar o valor na instancia para salvar posteriormente no DB
     * de uma unica vez (dentro do mesmo loop), otimizando a performance.
     *
     * Atenção: Nenhuma validação é feita. Se a validação dos campos falhar todas as propriedades adicionadas
     * a instanicia devem ser removidas.
     * */
    fun addPropriedadeNaInstancia(entradaUsuario: Double?, campo: Campo) =
            instancia.addPropriedade(Propriedade(campo.tipoCampo).apply {
                        valorDouble = entradaUsuario ?: 0.0
                    })

    /**
     * Essa função é usada para incluir a entrada do usuario na instancia do template no momento
     * da validação do valor que o usuario colocou no campo referente a essa propriedade.
     *
     *  A ideia é validar e registrar o valor na instancia para salvar posteriormente no DB
     * de uma unica vez (dentro do mesmo loop), otimizando a performance.
     *
     * Atenção: Nenhuma validação é feita. Se a validação dos campos falhar todas as propriedades adicionadas
     * a instanicia devem ser removidas.
     * */
    fun addPropriedadeNaInstancia(entradaUsuario: Boolean, campo: Campo) =
            instancia.addPropriedade(Propriedade(campo.tipoCampo).apply {
                        valorBoolean = entradaUsuario
                    })
}


