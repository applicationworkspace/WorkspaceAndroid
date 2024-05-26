package com.workspaceandroid.ui.feature.collection

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.model.GroupUIModel

class CollectionContract {

    sealed class Event : ViewEvent {
        object FetchUserGroupsWithPhrases : Event()
        data class AddButtonClicked(val email: String, val password: String) : Event()
        data class OnItemSelected(val selectedPhrase: PhraseModel) : Event()
        data class OnSearchInput(val searchText: String) : Event()
        data class OnPhraseRemove(val phraseId: Long) : Event()
        data class OnPhraseReset(val phraseId: Long) : Event()
        data class OnGroupSelected(val group: GroupUIModel) : Event()
    }

    data class State(
        val allGroupsWithPhrases: List<GroupUIModel> = emptyList(),
        val selectedPhrases: List<PhraseModel> = emptyList(),
//        val phrases: List<Phrase> = emptyList(),
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()
    }
}