package dev.gmarques.bancodedados.presenter.fragmento_principal

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dev.gmarques.bancodedados.App
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.data.repositorios.InstanciaRepo
import dev.gmarques.bancodedados.databinding.FragPrincipalBinding
import dev.gmarques.bancodedados.domain.modelos.template.Template
import dev.gmarques.bancodedados.presenter.fragmento_add_template.FragAddTemplate
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragPrincipal : Fragment(), MenuProvider {

    private lateinit var binding: FragPrincipalBinding
    private val viewModel: FragPrincipalViewModel by viewModels()


    private lateinit var adapter: TemplateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =

            FragPrincipalBinding.inflate(inflater, container, false).also {
                binding = it
            }.let {
                binding.root
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        atualizarToolbar()
        initBotaoAddObjeto()
        initRecyclerView()
        observarAtualizacoesNosTemplates()
        initMenu()
        initListenerDoFragmentoAddTemplate()
    }

    private fun initListenerDoFragmentoAddTemplate() {
        setFragmentResultListener(FragAddTemplate.CHAVE_RESULTADO) { chave: String, _: Bundle ->
            if (FragAddTemplate.CHAVE_RESULTADO == chave) {
                // TODO: atualizar recyclerview e depender de abstraçoes
                // TODO: O CampoRepo deve receber o template quando for salvar o campo
                // TODO: app nao ta salvando o template, só os campos
            }
        }
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this@FragPrincipal)
    }

    private fun observarAtualizacoesNosTemplates() {
        viewModel.templates.observe(viewLifecycleOwner) { itens ->
            adapter.atualizar(itens)
            Log.d("USUK", "FragPrincipal.observarAtualizacoesNosTemplates: ${itens.size}")
        }
    }

    private fun initRecyclerView() {
        val instanciaRepo = EntryPointAccessors
            .fromApplication(App.get.applicationContext, InstanciaRepo.InstanciaRepoEntryPoint::class.java)
            .getRepoInstancia()

        adapter = TemplateAdapter(this@FragPrincipal, instanciaRepo)
        binding.rvTemplates.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTemplates.adapter = adapter
    }

    private fun atualizarToolbar() {
        requireActivity().actionBar?.title = getString(R.string.Colecoes)
    }

    private fun initBotaoAddObjeto() {
        binding.fabAddObjeto.setOnClickListener {
            carregarTemplatesEMostrarDialogoSeMaisDeUm()
        }
    }

    private fun carregarTemplatesEMostrarDialogoSeMaisDeUm() =
            lifecycleScope.launch(IO) {

                val templates = viewModel.getTemplates()

                if (templates.size == 1) abrirFragmentoAdicionarInstancia(templates[0])
                else withContext(Main) {
                    DialogoEscolherInstanciaParaAdicionar(templates, this@FragPrincipal)
                    { abrirFragmentoAdicionarInstancia(it) }
                }
            }

    private fun abrirFragmentoAdicionarInstancia(template: Template) {
        val action = FragPrincipalDirections.actionAddInstancia(template)
        findNavController().navigate(action)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_frag_principal, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.novo_template -> findNavController().navigate(FragPrincipalDirections.actionAddTemplate())
        }
        return true
    }

    override fun onDestroyView() {
        val menuHost: MenuHost = requireActivity()
        menuHost.removeMenuProvider(this@FragPrincipal)
        super.onDestroyView()
    }


}