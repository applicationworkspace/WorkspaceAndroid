package com.workspaceandroid.ui.screens.settings

import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.AuthInteractor
import com.workspaceandroid.ui.screens.login.LoginContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsInteractor: AuthInteractor)
    : BaseViewModel<SettingsContract.Event, SettingsContract.SettingsState, SettingsContract.Effect>() {

    override fun setInitialState(): SettingsContract.SettingsState {
        TODO("Not yet implemented")
    }

    override fun handleEvents(event: SettingsContract.Event) {
        TODO("Not yet implemented")
    }
}