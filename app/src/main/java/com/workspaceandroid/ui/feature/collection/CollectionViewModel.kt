package com.workspaceandroid.ui.feature.collection

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.model.GroupUIModel
import com.workspaceandroid.model.mapper.GroupUIModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    private val groupUIModelMapper: GroupUIModelMapper
) : BaseViewModel<CollectionContract.Event, CollectionContract.State, CollectionContract.Effect>() {

    enum class SortType {
        AZ, DATE
    }

    private val allCollections: List<GroupUIModel>
        get() = viewState.value.allGroups

    override fun setInitialState(): CollectionContract.State =
        CollectionContract.State(isLoading = true)

    override fun handleEvents(event: CollectionContract.Event) {
        when (event) {
            is CollectionContract.Event.AddButtonClicked -> fetchUserGroups()
            is CollectionContract.Event.OnItemSelected -> updateExpandedCards(event.selectedPhrase)
            is CollectionContract.Event.OnSearchInput -> filterPhrases(event.searchText)
            is CollectionContract.Event.OnPhraseRemove -> deletePhrase(event.phraseId)
            is CollectionContract.Event.OnPhraseReset -> resetPhrase(event.phraseId)
            CollectionContract.Event.FetchUserGroupsWithPhrases -> fetchUserGroups()
        }
    }

    private fun updateExpandedCards(selectedPhrase: PhraseModel) {
        setState {
            val updatedCards = viewState.value.selectedPhrases.map { //TODO refactor
                if (it.id == selectedPhrase.id) it.copy(isExpanded = !it.isExpanded)
                else it
            }.toMutableList()
            copy(selectedPhrases = updatedCards)
        }
    }

    private fun filterPhrases(searchInput: String) {
        val selectedGroup = viewState.value.allGroups.firstOrNull { it.isSelected }
        setState {
            copy(selectedPhrases = allGroups
                .filter {
                    if (selectedGroup != null) {
                        it.id == selectedGroup.id
                    } else {
                       true
                    }
                }
                .flatMap { it.phrases}
                .filter { it.text.lowercase().contains(searchInput.lowercase()) }
            )
        }
    }

    fun onCollectionSelected(collection: GroupUIModel) {
        val phrasesFromCollection = allCollections.firstOrNull { it.id == collection.id }?.phrases
        val updatedGroups = allCollections.map { it.copy(isSelected = it.id == collection.id)}
        setState { copy(
            allGroups = updatedGroups,
            selectedPhrases = phrasesFromCollection ?: emptyList()
        ) }
    }

    private fun fetchUserGroups() {
        viewModelScope.launch {
            val allCollections = collectionInteractor.getUserGroups()
            setState {
                copy(
                    allGroups = allCollections.run(groupUIModelMapper::mapListTo),
                    selectedPhrases = allCollections.flatMap { it.phrases },
                    isLoading = false
                )
            }
        }
    }

    private fun deletePhrase(phraseId: Long) {
        viewModelScope.launch {
            collectionInteractor.removePhrase(phraseId)
        }
        val phrasesWithoutDeleted = allCollections
            .flatMap { it.phrases }
            .filter { it.id != phraseId }
        setState {
            copy(selectedPhrases = phrasesWithoutDeleted, isLoading = false)
        }
    }

    private fun resetPhrase(phraseId: Long) {
        viewModelScope.launch {
            collectionInteractor.updatePhraseRepeatStatus(phraseId, false)
            setEffect { CollectionContract.Effect.ShowToast("The phrase has been reset") }
            val updatedPhrases = allCollections
                .flatMap { it.phrases }
                .map { phrase ->
                    phrase.copy(isDone = false).takeIf { phrase.id == phraseId } ?: phrase
                }
            setState {
                copy(selectedPhrases = updatedPhrases)
            }
        }
    }

    private fun List<PhraseModel>.sortByDate(): List<PhraseModel> = this.sortedByDescending { it.createdAt }

}