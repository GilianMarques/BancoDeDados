package dev.gmarques.bancodedados.presenter.fragmento_add_template

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.gmarques.bancodedados.databinding.FragAddTemplateBinding
import dev.gmarques.bancodedados.databinding.TemplateRemoverCampoBinding
import dev.gmarques.bancodedados.domain.Nomes
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.presenter.fragmento_add_campo.FragAddCampo
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragAddTemplate : Fragment() {

    private lateinit var binding: FragAddTemplateBinding
    private val viewModel: FragAddTemplateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragAddTemplateBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFabAddCampo()
        initEdtNome()
        initListenerDoFragmentoAddCampo()
        exibirViewsDosCampos(viewModel.template.getCampos())
        initFabConcluir()
        observarMensagensDeErro()
        observarFecharApp()

    }

    private fun observarMensagensDeErro() {
        viewModel.mensagensDeErroLiveData.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(binding.fabAddCampo, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun observarFecharApp() {
        viewModel.fecharFrag.observe(viewLifecycleOwner) {
            if (it) definirResultadoESair()

        }
    }

    private fun initEdtNome() {

        if ((binding.edtNome.text?.length ?: 0) > 0) {
            binding.edtNome.requestFocus()
        }

        binding.edtNome.setOnFocusChangeListener { _: View, b: Boolean ->
            if (!b) binding.edtNome.setText(Nomes.adequarNome(binding.edtNome.text.toString()))
        }
    }

    private fun initFabConcluir() {
        binding.fabConcluir.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.template.getCampos().isEmpty()) return@launch

                viewModel.preencherTemplate(binding)

                if (viewModel.validarTemplate()) viewModel.salvarTemplate()

            }
        }


    }

    private fun definirResultadoESair() {
        setFragmentResult(CHAVE_RESULTADO, Bundle())
        findNavController().navigateUp()
    }

    private fun initListenerDoFragmentoAddCampo() {
        setFragmentResultListener(FragAddCampo.CHAVE_RESULTADO) { chave: String, bundle: Bundle ->
            if (FragAddCampo.CHAVE_RESULTADO == chave) {
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
            @Suppress("DEPRECATION") bundle.getSerializable(FragAddCampo.ID_RESULTADO) as Campo
        }.also {
            if (it == null) throw java.lang.NullPointerException("O campo nao pode ser nulo. Se o usuario nao adicionou um campo o setFragmentResultListener nao deve ser invocado")
        }!!
    }

    private fun initFabAddCampo() {
        binding.fabAddCampo.setOnClickListener {
            binding.edtNome.clearFocus()
            navegarParaFragmentoAdicionarCampo()
        }
    }

    private fun navegarParaFragmentoAdicionarCampo() {
        val action = FragAddTemplateDirections.actionAddCampo(viewModel.template)
        findNavController().navigate(action)
    }

    private fun exibirViewsDosCampos(campos: MutableList<Campo>) {

        if (binding.container.childCount == viewModel.template.getCampos().size) return

        campos.forEach { campo ->
            val vBinding = TemplateRemoverCampoBinding.inflate(layoutInflater)
            vBinding.tvNome.text = campo.nome
            vBinding.ivRemover.setOnClickListener {
                viewModel.template.removerCampo(campo)
                binding.container.removeView(vBinding.root)
                alternarVisibilidadeBotaoConcluir()
            }
            binding.container.addView(vBinding.root)
        }
        alternarVisibilidadeBotaoConcluir()
    }

    private fun alternarVisibilidadeBotaoConcluir() {

        binding.fabConcluir.visibility =
                if (binding.container.childCount > 0) View.VISIBLE else View.GONE

    }

    companion object {
        const val CHAVE_RESULTADO: String = "novo_template_criado"
    }
}