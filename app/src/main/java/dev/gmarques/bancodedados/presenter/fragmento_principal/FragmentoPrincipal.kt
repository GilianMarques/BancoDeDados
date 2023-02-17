package dev.gmarques.bancodedados.presenter.fragmento_principal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class FragmentoPrincipal : Fragment() {

    private lateinit var binding: FragPrincipalBinding
    private lateinit var viewModel: FragmentoPrincipalViewModel
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

        viewModel = ViewModelProvider(this).get(FragmentoPrincipalViewModel::class.java)

        atualizarToolbar()
        initBotaoAddObjeto()
        initRecyclerView()
        ouvirAtualizacoesNosTemplates()
    }

    private fun ouvirAtualizacoesNosTemplates() {
        viewModel.templates.observe(viewLifecycleOwner) { itens ->
            adapter.atualizar(itens)
        }
    }

    private fun initRecyclerView() {
        adapter = TemplateAdapter(this@FragmentoPrincipal)
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
                DialogoEscolherInstanciaParaAdicionar(templates, this@FragmentoPrincipal)
                { abrirFragmentoAdicionarInstancia(it) }
            }
        }


    private fun abrirFragmentoAdicionarInstancia(template: Template) {
        val action = FragmentoPrincipalDirections.actionAddInstancia(template)
        findNavController().navigate(action)
    }


}