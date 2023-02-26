package dev.gmarques.bancodedados.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Sempre que esse livedata recebe um valor ele emite a atualização e depoi9s define seu valor como nulo
 * reemitindo a atualização dessa vez nula. Os observadores devem verificar se o valor recebido é nulo antes
 * de usar esse valor para algo.
 * Essa classe é uma "solução" simples para ser usadas com Snackbars ou Toasts na UI
 * */
class SingleShotLiveData<T> : MutableLiveData<T?>() {

    /**
     *  um observer que define o valor como null sempre que recebe um update nao nulo do livedata
     */
    private val observador = Observer<T?> { t ->
        t?.let {
            value = null
        }
    }

    @Volatile
    private var observado = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        super.observe(owner, observer)
        if (!observado) {
            super.observe(owner, observador)
            observado = true
        }
    }

    override fun observeForever(observer: Observer<in T?>) {
        super.observeForever(observer)
        if (!observado) {
            super.observeForever(observador)
            observado = true
        }
    }


}