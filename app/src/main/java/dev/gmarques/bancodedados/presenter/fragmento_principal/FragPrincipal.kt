package dev.gmarques.bancodedados.presenter.fragmento_principal

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.data.repositorios.InstanciaRepo
import dev.gmarques.bancodedados.databinding.FragPrincipalBinding
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FragPrincipal : Fragment(), MenuProvider {

    private lateinit var binding: FragPrincipalBinding
    private val viewModel: FragPrincipalViewModel by viewModels()

    @Inject
    lateinit var instanciasRepo: InstanciaRepo

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
        adapter = TemplateAdapter(this@FragPrincipal, instanciasRepo)
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
        lifecycleScope.launch(Dispatchers.IO) {

            val templates = viewModel.getTemplates()

            if (templates.size == 1) abrirFragmentoAdicionarInstancia(templates[0])
            else withContext(Dispatchers.Main) {
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