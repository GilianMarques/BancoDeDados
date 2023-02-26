package dev.gmarques.bancodedados.presenter.fragmento_add_template

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gmarques.bancodedados.domain.modelos.template.Template
import javax.inject.Inject

@HiltViewModel
class FragAddTemplateViewModel @Inject constructor() : ViewModel() {
    val template = Template()

}
