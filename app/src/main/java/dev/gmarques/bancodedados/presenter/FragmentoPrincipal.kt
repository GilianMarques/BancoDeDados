package dev.gmarques.bancodedados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.gmarques.bancodedados.databinding.FragPrincipalBinding
import dev.gmarques.bancodedados.presenter.FragmentoPrincipalViewModel


class FragmentoPrincipal : Fragment() {
// TODO: popular a tela cm dados dos tempaltes

    private lateinit var binding: FragPrincipalBinding
    private lateinit var viewModel: FragmentoPrincipalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =

        FragPrincipalBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }
            .let {
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
        viewModel.
    }

    private fun initRecyclerView() {


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