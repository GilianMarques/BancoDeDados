package dev.gmarques.bancodedados.presenter.fragmento_add_campo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.databinding.FragAddCampoBinding
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.*

class FragAddCampoViewModel : ViewModel() {

    lateinit var template: Template

    private val _erroDeEntrada = MutableLiveData(0)
    val erroDeEntrada: LiveData<Int> get() = _erroDeEntrada

    fun validarNome(nome: String?) = Campo.validarNome(nome)
        .also {
            if (!it) _erroDeEntrada.value = R.string.Nome_invalido
        }

    fun validarRegrasNumero(maiorQue: Int?, menorQue: Int?) =
        Campo.validarRegrasNumero(maiorQue, menorQue)
            .also {
                if (!it) _erroDeEntrada.value = R.string.Entrada_invalida_para_regras_de_numero
            }

    fun validarRegrasTexto(compMaximo: Int?, compMinimo: Int?) =
        Campo.validarRegrasTexto(compMaximo, compMinimo)
            .also {
            if (!it) _erroDeEntrada.value = R.string.Entrada_invalida_para_regras_de_texto
        }

    fun criarObjetoCampo(binding: FragAddCampoBinding) = Campo(template.uid, inferirTipo(binding))
        .apply {
            nome = binding.edtNome.text!!.toString()

            podeSerVazio = binding.swPodeFicarVazio.isChecked

            maiorQue = binding.edtMaiorQue.text?.toString()?.toIntOrNull() ?: MAIOR_QUE_PADRAO
            menorQue = binding.edtMenorQue.text?.toString()?.toIntOrNull() ?: MENOR_QUE_PADRAO

            comprimentoMaximo = binding.edtComprimentoMax.text
                ?.toString()?.toIntOrNull() ?: COMPRIMENTO_MAXIMO_PADRAO

            comprimentoMinimo = binding.edtComprimentoMin.text
                ?.toString()?.toIntOrNull() ?: COMPRIMENTO_MINIMO_PADRAO
        }

    /**
     * Obtem o valor adequado do enum de tipo de campo verificando qual dos chips foi selecionado pelo usuario
     * Essa função só deve ser chamada quando houver certeza que um dois chips esta selecionado
     *
     * @throws Exception se nenhum dos 3 chips validos estiver selecionados
     * */
    private fun inferirTipo(binding: FragAddCampoBinding) = when (binding.chipGroup.checkedChipId) {
        R.id.chip_texto -> TipoCampo.TEXTO
        R.id.chip_numero -> TipoCampo.NUMERO
        R.id.chip_boolean -> TipoCampo.BOOLEANO
        else -> throw Exception("Nenhum dos chips foi selecionado pelo usuario. Id retornada pelo ChipGroup: '${binding.chipGroup.checkedChipId}'")
    }
}