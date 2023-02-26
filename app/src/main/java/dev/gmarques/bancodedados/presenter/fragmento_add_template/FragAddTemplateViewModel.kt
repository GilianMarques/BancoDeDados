package dev.gmarques.bancodedados.presenter.fragmento_add_template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gmarques.bancodedados.App
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.data.repositorios.CamposRepo
import dev.gmarques.bancodedados.data.repositorios.TemplatesRepo
import dev.gmarques.bancodedados.databinding.FragAddTemplateBinding
import dev.gmarques.bancodedados.domain.Nomes
import dev.gmarques.bancodedados.domain.modelos.template.Template
import dev.gmarques.bancodedados.presenter.SingleShotLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragAddTemplateViewModel @Inject constructor(
    private val templateRepo: TemplatesRepo,
    private val camposRepo: CamposRepo,
) : ViewModel() {

    val template = Template()

    private val _fecharFrag = MutableLiveData<Boolean>()
    val fecharFrag: LiveData<Boolean> get() = _fecharFrag

    private val _mensagensDeErroLiveData = SingleShotLiveData<String?>()
    val mensagensDeErroLiveData: LiveData<String?> get() = _mensagensDeErroLiveData

    fun preencherTemplate(binding: FragAddTemplateBinding) {
        template.nome = binding.edtNome.text.toString()
    }

    fun validarTemplate(): Boolean {
        return validarNome()
    }

    private fun validarNome(): Boolean {
        return Nomes.adequarNome(template.nome).isNotBlank().also {
            if (!it) _mensagensDeErroLiveData.value =
                    App.get.applicationContext.getString(R.string.Verifique_o_nome_do_template_e_tente_novamente)

        }
    }

    fun salvarTemplate() = viewModelScope.launch(IO) {
        template.getCampos().forEach { camposRepo.addOuAtualizar(it,template) }
        templateRepo.addOuAtualizar(template)
        _fecharFrag.postValue(true)
    }

}
