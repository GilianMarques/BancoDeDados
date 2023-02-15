package dev.gmarques.bancodedados.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.gmarques.bancodedados.data.repositorios.TemplatesRepo
import dev.gmarques.bancodedados.domain.modelos.template.Template

class FragmentoPrincipalViewModel : ViewModel() {


    private val mutableTemplates = MutableLiveData<ArrayList<Template>>()

    val templates: LiveData<ArrayList<Template>> get() = mutableTemplates

    fun carregarTemplates(){
        val templates = TemplatesRepo.carregarTemplates()
    }

}