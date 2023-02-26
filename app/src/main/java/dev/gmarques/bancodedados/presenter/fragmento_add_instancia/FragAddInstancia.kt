package dev.gmarques.bancodedados.presenter.fragmento_add_instancia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.databinding.FragAddInstanciaBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoBooleanoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoNumericoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoTextoBinding
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.*
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.COMPRIMENTO_MAXIMO_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.COMPRIMENTO_MINIMO_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.MAIOR_QUE_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.MENOR_QUE_PADRAO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.PODE_SER_VAZIO_PADRAO
import kotlinx.coroutines.launch

class FragAddInstancia : Fragment() {

    private lateinit var binding: FragAddInstanciaBinding
    private lateinit var viewModel: FragAddInstanciaViewModel

    private val args: FragAddInstanciaArgs by navArgs()
    private val views = ArrayList<ViewBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        viewModel = ViewModelProvider(this).get(FragAddInstanciaViewModel::class.java)
        binding = FragAddInstanciaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.template = args.template

        atualizarToolbar()
        initBotaoAdd()
        carregarUi()

    }

    private fun initBotaoAdd() {
        binding.fabAddObjeto.setOnClickListener {

            binding.root.clearFocus()

            lifecycleScope.launch {
                if (viewModel.validarEntradas(views)) {
                    viewModel.salvarObjeto(views)
                    this@FragAddInstancia.findNavController().navigateUp()
                } else notificar(getString(R.string.Verifique_os_valores_inseridos_e_tente_novamente))
            }
        }
    }

    private fun atualizarToolbar() {

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            String.format(getString(R.string.Novo_a_instancia), viewModel.template.nome)


    }

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

    private fun lerRegrasDoCampo(campo: Campo): String {
        val regras = StringBuilder()
        regras.appendLine(getString(R.string.O_campo_deve))

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
            {
                if (campo.comprimentoMaximo != COMPRIMENTO_MAXIMO_PADRAO) regras.appendLine(
                    String.format(
                        getString(R.string.Ter_comprimento_menor_que_x), campo.comprimentoMaximo
                    )
                )
                if (campo.comprimentoMinimo != COMPRIMENTO_MINIMO_PADRAO) regras.appendLine(
                    String.format(
                        getString(R.string.Ter_comprimento_maior_que_x), campo.comprimentoMinimo
                    )
                )
            }
        }

        when (campo.tipoCampo) {
            TipoCampo.NUMERO -> regrasDeCampoNumerico()
            TipoCampo.TEXTO -> regrasDeCampoDeTexto()
            TipoCampo.BOOLEANO -> {}
        }

        return regras.toString()

    }

    private fun notificar(mensagem: String) {
        Snackbar.make(binding.fabAddObjeto, mensagem, Snackbar.LENGTH_LONG).show()
    }


}