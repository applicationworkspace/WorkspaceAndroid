package com.workspaceandroid.ui.screens.collection

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.UserCollection

class CollectionContract {

    sealed class Event : ViewEvent {
        data class AddButtonClicked(val email: String, val password: String) : Event()
        data class OnItemSelected(val selectedPhrase: Phrase) : Event()
        data class OnSearchInput(val searchText: String) : Event()
        data class OnPhraseRemove(val phraseId: Long) : Event()
        data class OnPhraseReset(val phraseId: Long) : Event()
    }

    data class State(
        val allCollections: List<UserCollection> = emptyList(),
        val phrases: List<Phrase> = emptyList(),
//        val phrases: List<Phrase> = emptyList(),
        val isLoading: Boolean = false
    ) : ViewState

//        data class State(
//        val collectionState: CollectionState
//        val isLoading = true
//    ) : ViewState
//
//    sealed class CollectionState {
//        object Loading : CollectionState()
//        data class Success(
//
//        ) : CollectionState()
//
//        data class Error(val errorMessage: String) : CollectionState()
//    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()
    }
}