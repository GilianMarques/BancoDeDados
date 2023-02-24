package dev.gmarques.bancodedados.presenter.fragmento_add_template

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.gmarques.bancodedados.databinding.FragAddTemplateBinding
import dev.gmarques.bancodedados.databinding.TemplateRemoverCampoBinding
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.presenter.fragmento_add_campo.FragAddCampo

class FragAddTemplate : Fragment() {


    private lateinit var binding: FragAddTemplateBinding
    private lateinit var viewModel: FragAddTemplateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragAddTemplateBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FragAddTemplateViewModel::class.java)

        initFabAddCampo()
        initListenerDoFragmentoAddCampo()
        exibirViewsDosCampos(viewModel.template.getCampos())

        binding.edtNome.requestFocus()
    }

    private fun initListenerDoFragmentoAddCampo() {
        setFragmentResultListener(FragAddCampo.CHAVE_RESULTADO) { chave: String, bundle: Bundle ->
            if (FragAddCampo.CHAVE_RESULTADO.equals(chave)) {
                val campo = getCampo(bundle)
                viewModel.template.addCampo(campo)
                exibirViewsDosCampos(arrayListOf(campo))
            }
        }
    }

    /**
     * Extrai do pacote o objeto campo que o usuario acabou de adicionar com
     * base na versao do sistema operacional do aparelho
     * @throws NullPointerException se o objeto campo extraido do pacote for nulo
     * */
    private fun getCampo(bundle: Bundle): Campo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getSerializable(FragAddCampo.ID_RESULTADO, Campo::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getSerializable(FragAddCampo.ID_RESULTADO) as Campo
        }.also {
            if (it == null) throw java.lang.NullPointerException("O campo nao pode ser nulo. Se o usuario nao adicionou um campo o setFragmentResultListener nao deve ser invocado")
        }!!
    }

    private fun initFabAddCampo() {
        binding.fabAddCampo.setOnClickListener {
            navegarParaFragmentoAdcionarCampo()
        }
    }

    private fun navegarParaFragmentoAdcionarCampo() {
        val action = FragAddTemplateDirections.actionAddCampo(viewModel.template)
        findNavController().navigate(action)
    }

    private fun exibirViewsDosCampos(campos: MutableList<Campo>) {

        if (binding.container.childCount == viewModel.template.getCampos().size) return

        campos.forEach { campo ->
            val vBinding = TemplateRemoverCampoBinding.inflate(layoutInflater)
            vBinding.tvNome.text = campo.nome
            binding.container.addView(vBinding.root)
        }
    }

}