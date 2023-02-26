package dev.gmarques.bancodedados.presenter.fragmento_principal

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.EntryPoint
import dev.gmarques.bancodedados.R
import dev.gmarques.bancodedados.data.repositorios.InstanciaRepo
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.databinding.ItemTemplateBinding
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TemplateAdapter(
    private val fragmento: Fragment,
    private val instanciasRepo: InstanciaRepo,
) :
    RecyclerView.Adapter<TemplateAdapter.Holder>() {

    val itens = ArrayList<Template>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(ItemTemplateBinding.inflate(fragmento.layoutInflater, parent, false))

    override fun getItemCount() = itens.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    fun atualizar(itens: ArrayList<Template>) {
        this.itens.clear()
        this.itens.addAll(itens)
        notifyDataSetChanged()
    }

    inner class Holder(val itemBinding: ItemTemplateBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(position: Int): Any = fragmento.lifecycleScope.launch(IO) {

            val template = itens[position]
            val instancias = instanciasRepo.contarInstancias(template.uid)

            withContext(Main) {
                itemBinding.tvNome.text = template.nome
                itemBinding.tvQuantidade.text = String.format(
                    fragmento.getString(R.string.X_objetos_registrados),
                    instancias
                )
            }
        }


    }
}