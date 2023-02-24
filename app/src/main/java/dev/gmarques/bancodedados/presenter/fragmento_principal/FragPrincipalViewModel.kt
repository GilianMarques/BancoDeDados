package dev.gmarques.bancodedados.presenter.fragmento_principal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gmarques.bancodedados.data.repositorios.TemplatesRepo
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.launch

class FragPrincipalViewModel : ViewModel() {


    private val mutableTemplates = MutableLiveData<ArrayList<Template>>()
    val templates: LiveData<ArrayList<Template>> get() = mutableTemplates


    init {
        viewModelScope.launch { carregarTemplates() }
    }

    suspend fun carregarTemplates() {
        Log.d("USUK", "FragmentoPrincipalViewModel.carregarTemplates: lendo db")
        mutableTemplates.value = TemplatesRepo.carregarTemplates()
    }

    suspend fun getTemplates() = TemplatesRepo.carregarTemplates()

}