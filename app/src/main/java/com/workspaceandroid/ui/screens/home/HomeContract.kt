package com.workspaceandroid.ui.screens.home

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.Phrase

class HomeContract {

    sealed class Event : ViewEvent {
        data class onButtonClicked(val text: String) : Event()
    }

    sealed class HomeState : ViewState {
        object Loading : HomeState()
        data class Error(val errorMessage: String) : HomeState()
        data class Success(val phrases: List<Phrase>) : HomeState()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()
//        sealed class Navigation : Effect() {
//            data class ToMain(val userLogin: String): Navigation()
//        }
    }
}