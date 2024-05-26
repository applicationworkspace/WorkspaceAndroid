package com.workspaceandroid.ui.feature.add_phrase

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.models.phrase.PhraseInput

class AddPhraseContract {

    sealed class Event : ViewEvent {
        object OnSaveButtonClicked : Event()
        data class OnPhraseUpdated(val phraseBuilder: PhraseInput.() -> Unit) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val predictedPhrase: PhraseModel? = null,
        val userCollections: List<Pair<Long, String>> = emptyList(),
        val selectedCollectionChip: Long = -1
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()

        sealed class Navigation : Effect() {
            data class Back(val userSearch: String): Navigation()
        }
    }
}