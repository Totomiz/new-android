package com.tz.mad.ui.kosample

import androidx.lifecycle.ViewModel
import com.tz.mad.domain.KoUseCase

class KoViewModel(val useCase: KoUseCase):ViewModel() {

    fun sayHello(name : String) : String{
        val foundUser = useCase.repo.findUser(name)
        return foundUser?.let { "Hello '$it' from $this" } ?: "User '$name' not found!"
    }
}