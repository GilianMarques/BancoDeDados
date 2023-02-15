package dev.gmarques.bancodedados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.gmarques.bancodedados.databinding.FragPrincipalBinding
import dev.gmarques.bancodedados.presenter.FragmentoPrincipalViewModel
import dev.gmarques.bancodedados.presenter.TemplateAdapter
import kotlinx.coroutines.launch


class FragmentoPrincipal : Fragment() {
// TODO: popular a tela cm dados dos tempaltes

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
        lifecycleScope.launch { viewModel.carregarTemplates() }
    }

    private fun atualizarToolbar() {
        requireActivity().actionBar?.title = getString(R.string.Colecoes)
    }

    private fun initBotaoAddObjeto() {
        binding.fabAddObjeto.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


}