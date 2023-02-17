package dev.gmarques.bancodedados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dev.gmarques.bancodedados.databinding.FragAddInstanciaBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoBooleanoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoNumericoBinding
import dev.gmarques.bancodedados.databinding.InstanciaCampoTextoBinding
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Entrada
import dev.gmarques.bancodedados.domain.modelos.template.Template

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentoAddInstancia : Fragment() {

    private lateinit var binding: FragAddInstanciaBinding
    val args: FragmentoAddInstanciaArgs by navArgs()
    lateinit var template: Template

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragAddInstanciaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        template = args.template

        atualizarToolbar()
        carregarUi()

    }

    private fun atualizarToolbar() {

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            String.format(getString(R.string.Novo_a_instancia), template.nome)


    }

    private fun carregarUi() {
        template.entradas.forEach { entrada ->
            when (entrada.tipoCampo) {
                TipoCampo.NUMERO -> criarCampoNumerico(entrada)
                TipoCampo.TEXTO -> criarCampoDeTexto(entrada)
                TipoCampo.REAL -> criarCampoBooleano(entrada)
            }
        }

    }

    private fun criarCampoNumerico(entrada: Entrada) {
        val view = InstanciaCampoNumericoBinding.inflate(layoutInflater)
        view.edtEntrada.hint = entrada.nome
        view.edtEntrada.tag = entrada.uid
        binding.container.addView(view.root)
    }

    private fun criarCampoDeTexto(entrada: Entrada) {
        val view = InstanciaCampoTextoBinding.inflate(layoutInflater)
        view.edtEntrada.hint = entrada.nome
        view.edtEntrada.tag = entrada.uid
        binding.container.addView(view.root)
    }

    private fun criarCampoBooleano(entrada: Entrada) {
        val view = InstanciaCampoBooleanoBinding.inflate(layoutInflater)
        view.swEntrada.text = entrada.nome
        view.swEntrada.tag = entrada.uid
        binding.container.addView(view.root)
    }


}