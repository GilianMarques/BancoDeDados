package dev.gmarques.bancodedados.presenter.fragmento_principal

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.databinding.FragPrincipalBinding
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragPrincipal : Fragment(), MenuProvider {

    private lateinit var binding: FragPrincipalBinding
    private lateinit var viewModel: FragPrincipalViewModel
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

        viewModel = ViewModelProvider(this)[FragPrincipalViewModel::class.java]

        atualizarToolbar()
        initBotaoAddObjeto()
        initRecyclerView()
        ouvirAtualizacoesNosTemplates()
        initMenu()
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this@FragPrincipal)
    }

    private fun ouvirAtualizacoesNosTemplates() {
        viewModel.templates.observe(viewLifecycleOwner) { itens ->
            adapter.atualizar(itens)
        }
    }

    private fun initRecyclerView() {
        adapter = TemplateAdapter(this@FragPrincipal)
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


}