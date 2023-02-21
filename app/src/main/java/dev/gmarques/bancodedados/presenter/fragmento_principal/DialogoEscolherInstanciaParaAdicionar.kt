package dev.gmarques.bancodedados.presenter.fragmento_principal

import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.databinding.ItemAddTemplateBinding
import dev.gmarques.bancodedados.domain.modelos.template.Template

class DialogoEscolherInstanciaParaAdicionar(
    val templates: ArrayList<Template>,
    val fragmento: Fragment,
    val callback: (Template) -> Unit,
) {


    private lateinit var dialogo: AlertDialog

    init {
        mostrarDialogo()
    }

    private fun mostrarDialogo() {


        dialogo = MaterialAlertDialogBuilder(fragmento.requireContext())
            .setTitle(fragmento.requireContext().getString(R.string.O_que_deseja_adicionar))
            .setView(carregarViews(templates))
            .create()
        dialogo.show()

    }

    private fun carregarViews(templates: ArrayList<Template>): LinearLayout {

        val container = criarContainerParaAsViewsDeInstancia()

        templates.forEach { template ->
            val view = ItemAddTemplateBinding.inflate(fragmento.layoutInflater)
            view.fabNome.text = template.nome
            view.fabNome.setOnClickListener {
                dialogo.dismiss()
                callback.invoke(template)
            }
            container.addView(view.root)
        }

        return container
    }

    private fun criarContainerParaAsViewsDeInstancia() = LinearLayout(fragmento.requireContext())
        .apply {
            setPadding(16, 48, 16, 16)
            orientation = LinearLayout.VERTICAL
        }
}