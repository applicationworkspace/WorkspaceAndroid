package com.workspaceandroid.ui.feature.home

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.PhraseModel

class HomeContract {

    sealed class Event : ViewEvent {
        data class onButtonClicked(val text: String) : Event()
    }

    sealed class HomeState : ViewState {
        object Loading : HomeState()
        data class Error(val errorMessage: String) : HomeState()
        data class Success(val phrases: List<PhraseModel>) : HomeState()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()
//        sealed class Navigation : Effect() {
//            data class ToMain(val userLogin: String): Navigation()
//        }
    }
}