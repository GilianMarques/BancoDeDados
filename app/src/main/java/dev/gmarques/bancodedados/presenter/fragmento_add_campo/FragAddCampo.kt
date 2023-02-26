package dev.gmarques.bancodedados.presenter.fragmento_add_campo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.gmarques.bancodedados.databinding.FragAddCampoBinding
@AndroidEntryPoint
class FragAddCampo : Fragment() {


    private lateinit var binding: FragAddCampoBinding
    private val viewModel: FragAddCampoViewModel by viewModels()
    private val args: FragAddCampoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragAddCampoBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.template = args.template

        initChipTexto()
        initChipNumero()
        initChipBooleano()
        initFabConcluir()
        observarErrosDeEntrada()

        binding.edtNome.requestFocus()

    }

    private fun initChipTexto() {
        val regras = arrayListOf(
            binding.tiComprimentoMax, binding.tiComprimentoMin, binding.swPodeFicarVazio
        )

        binding.chipTexto.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if (b) exibirViews(regras)
        }

    }

    private fun initChipNumero() {
        val regras = arrayListOf(
            binding.tiMaiorQue, binding.tiMenorQue, binding.swPodeFicarVazio
        )

        binding.chipNumero.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if (b) exibirViews(regras)
        }

    }

    private fun initChipBooleano() {
        val regras = arrayListOf<View>()

        binding.chipBoolean.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if (b) exibirViews(regras)
        }

    }

    private fun initFabConcluir() {
        binding.fabConcluir.setOnClickListener {
            if (validarEntradaDoUsuario()) salvarResultadoESair()
        }
    }

    private fun exibirViews(viewParaExibir: ArrayList<View>) {
        viewParaExibir.add(binding.tvRegras)

        val todasAsregras = arrayListOf(
            binding.tiComprimentoMax,
            binding.tiComprimentoMin,
            binding.tiMaiorQue,
            binding.tiMenorQue,
            binding.swPodeFicarVazio
        )

        todasAsregras.forEach { it.visibility = View.GONE }
        viewParaExibir.forEach { it.visibility = View.VISIBLE }
    }

    private fun observarErrosDeEntrada() {
        viewModel.erroDeEntrada.observe(viewLifecycleOwner) {
            if (it != 0) Snackbar.make(binding.fabConcluir, getString(it), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun validarEntradaDoUsuario(): Boolean {

        if (binding.chipGroup.checkedChipId == View.NO_ID) return false

        if (!viewModel.validarNome(binding.edtNome.text?.toString())) return false

        val compMax = binding.edtComprimentoMax.text.toString().toIntOrNull()
        val compMin = binding.edtComprimentoMin.text.toString().toIntOrNull()

        if (binding.chipTexto.isChecked && !viewModel.validarRegrasTexto(
                compMax,
                compMin
            )
        ) return false

        val maiorQue = binding.edtMaiorQue.text.toString().toIntOrNull()
        val menorQue = binding.edtMenorQue.text.toString().toIntOrNull()

        if (binding.chipNumero.isChecked && !viewModel.validarRegrasNumero(
                maiorQue,
                menorQue
            )
        ) return false

        return true
    }

    private fun salvarResultadoESair() {
        val campo = viewModel.criarObjetoCampo(binding)

        val pacote = Bundle().apply { putSerializable(ID_RESULTADO, campo) }
        setFragmentResult(CHAVE_RESULTADO, pacote)

        findNavController().navigateUp()
    }


    companion object {
        val ID_RESULTADO: String = "campo"
        val CHAVE_RESULTADO: String = "novo_campo_criado"
    }

}