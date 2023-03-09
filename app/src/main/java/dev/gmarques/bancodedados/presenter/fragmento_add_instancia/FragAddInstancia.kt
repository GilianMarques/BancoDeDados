package dev.gmarques.bancodedados.presenter.fragmento_add_instancia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.databinding.FragAddInstanciaBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoBooleanoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoNumericoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoTextoBinding
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.COMPRIMENTO_MAXIMO_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.COMPRIMENTO_MINIMO_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.MAIOR_QUE_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.MENOR_QUE_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.PODE_SER_VAZIO_PADRAO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragAddInstancia : Fragment() {

    private lateinit var binding: FragAddInstanciaBinding
    private val viewModel: FragAddInstanciaViewModel by viewModels()

    private val args: FragAddInstanciaArgs by navArgs()
    private val views = ArrayList<ViewBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragAddInstanciaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.template = args.template

        atualizarToolbar()
        initBotaoConcluir()
        carregarUi()
        observarValidareSalvar()

    }

    /**
     * Define um observador que monitora o livedata responsavel por informar se a validaçao e
     * persistencia da instancia foram feitos, se sim, o fragmento é fechado, se não uma mensagem
     * de erro genérica é exibida ao usuario para revisar os dados que ele inseriu no formulario
     */
    private fun observarValidareSalvar() {
        viewModel.validarESalvar.observe(viewLifecycleOwner) {
            it?.let {
                if (it) findNavController().navigateUp()
                else notificar(getString(R.string.Verifique_os_valores_inseridos_e_tente_novamente))
            }
        }

    }

    private fun atualizarToolbar() {

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
                String.format(getString(R.string.Novo_a_instancia), viewModel.template.nome)


    }

    /**
     * Carrega todas as views referentes ao campos da instancia
     * */
    private fun carregarUi() {
        viewModel.template.getCampos().forEach { entrada ->
            when (entrada.tipoCampo) {
                TipoCampo.NUMERO -> criarCampoNumerico(entrada)
                TipoCampo.TEXTO -> criarCampoDeTexto(entrada)
                TipoCampo.BOOLEANO -> criarCampoBooleano(entrada)
            }
        }

    }

    private fun criarCampoNumerico(campo: Campo) {
        val view = InstanciaCampoNumericoBinding.inflate(layoutInflater)
        view.tiEntrada.hint = campo.nome
        view.root.tag = campo
        view.tvRegras.text = lerRegrasDoCampo(campo)
        binding.container.addView(view.root)
        views.add(view)
    }

    private fun criarCampoDeTexto(campo: Campo) {
        val view = InstanciaCampoTextoBinding.inflate(layoutInflater)
        view.tiEntrada.hint = campo.nome
        view.root.tag = campo
        view.tvRegras.text = lerRegrasDoCampo(campo)
        binding.container.addView(view.root)
        views.add(view)
    }

    private fun criarCampoBooleano(campo: Campo) {
        val view = InstanciaCampoBooleanoBinding.inflate(layoutInflater)
        view.swEntrada.text = campo.nome
        view.root.tag = campo
        binding.container.addView(view.root)
        views.add(view)
    }

    /**
     * Cria uma sequencia legivel das regras do campo, verificando se o valor de regra é diferente do
     * padrao pra nao exibir informações desnecessarias pro usuario
     * */
    private fun lerRegrasDoCampo(campo: Campo): String {

        val regras = StringBuilder().appendLine(getString(R.string.O_campo_deve))

        if (campo.podeSerVazio == PODE_SER_VAZIO_PADRAO) regras.appendLine(getString(R.string.Ter_algum_conteudo))

        val regrasDeCampoNumerico = {
            if (campo.maiorQue != MAIOR_QUE_PADRAO) regras.appendLine(
                String.format(
                    getString(R.string.Ser_maior_que_x), campo.maiorQue
                )
            )
            if (campo.menorQue != MENOR_QUE_PADRAO) regras.appendLine(
                String.format(
                    getString(R.string.Ser_menor_que_x), campo.menorQue
                )
            )
        }

        val regrasDeCampoDeTexto = {

            if (campo.comprimentoMaximo != COMPRIMENTO_MAXIMO_PADRAO) regras.appendLine(
                String.format(
                    getString(R.string.Ter_comprimento_maior_que_x), campo.comprimentoMaximo
                )
            )
            if (campo.comprimentoMinimo != COMPRIMENTO_MINIMO_PADRAO) regras.appendLine(
                String.format(
                    getString(R.string.Ter_comprimento_menor_que_x), campo.comprimentoMinimo
                )
            )
        }

        when (campo.tipoCampo) {
            TipoCampo.NUMERO -> regrasDeCampoNumerico.invoke()
            TipoCampo.TEXTO -> regrasDeCampoDeTexto.invoke()
            TipoCampo.BOOLEANO -> {
                Log.d("USUK", "FragAddInstancia.lerRegrasDoCampo: esse campo nao tem regras")
            }
        }

        return regras.toString()

    }

    private fun initBotaoConcluir() {
        binding.fabAddObjeto.setOnClickListener {

            binding.root.clearFocus()


            if (validarCampos()) viewModel.salvarInstancia()
            else viewModel.instancia.removerTodasAsPropriedades()
        }

    }

    private fun validarCampos(): Boolean {

        views.forEach {
            when (it) {
                is InstanciaCampoNumericoBinding -> if (!validarCampoNumerico(it)) return false
                is InstanciaCampoTextoBinding -> if (!validarCampoDeTexto(it)) return false
                is InstanciaCampoBooleanoBinding -> validarCampoBooleano(it)
            }
        }
        return true
    }

    /**
     * Extrai os dados do campo e solicita ao viewModel que salve na instancia retornando
     * true pois nao é necessario fazer nenhuma validação em campos booleanos
     * */
    private fun validarCampoBooleano(instanciaCampoBooleano: InstanciaCampoBooleanoBinding): Boolean {

        val campo = instanciaCampoBooleano.root.tag as Campo
        val entradaUsuario = instanciaCampoBooleano.swEntrada.isChecked

        viewModel.addPropriedadeNaInstancia(entradaUsuario, campo)

        return true
    }

    /**
     * Extrai os dados do campo de texto e solicita ao viewModel que salve na instancia e valide as informações
     * de acordo com as regras de negocio do campo. Exibe uma mensagem de erro alem de exibir
     * as regras do campo ao usuario caso a validação retorne false
     * */
    private fun validarCampoDeTexto(instanciaCampoTexto: InstanciaCampoTextoBinding): Boolean {

        instanciaCampoTexto.tvRegras.visibility = View.GONE

        val campo = instanciaCampoTexto.root.tag as Campo
        val entradaUsuario = instanciaCampoTexto.edtEntrada.text.toString()

        viewModel.addPropriedadeNaInstancia(entradaUsuario, campo)

        return if (viewModel.validarCampoDeTexto(entradaUsuario, campo)) true
        else {
            instanciaCampoTexto.tvRegras.visibility = View.VISIBLE
            instanciaCampoTexto.edtEntrada.requestFocus()
            notificar(getString(R.string.Verifique_os_valores_inseridos_e_tente_novamente))
            false
        }
    }

    /**
     * Extrai os dados do campo numerico e solicita ao viewModel que salve na instancia e valide as informações
     * de acordo com as regras de negocio do campo. Exibe uma mensagem de erro alem de exibir
     * as regras do campo ao usuario caso a validação retorne false
     * */
    private fun validarCampoNumerico(instanciaCampoNumerico: InstanciaCampoNumericoBinding): Boolean {

        instanciaCampoNumerico.tvRegras.visibility = View.GONE

        val campo = instanciaCampoNumerico.root.tag as Campo
        val entradaUsuario = instanciaCampoNumerico.edtEntrada.text.toString().toDoubleOrNull()

        viewModel.addPropriedadeNaInstancia(entradaUsuario, campo)

        return if (viewModel.validarCampoNumerico(entradaUsuario, campo)) true
        else {
            instanciaCampoNumerico.tvRegras.visibility = View.VISIBLE
            instanciaCampoNumerico.edtEntrada.requestFocus()
            notificar(getString(R.string.Verifique_os_valores_inseridos_e_tente_novamente))
            false
        }
    }

    private fun notificar(mensagem: String) {
        Snackbar.make(binding.fabAddObjeto, mensagem, Snackbar.LENGTH_LONG).show()
    }


}