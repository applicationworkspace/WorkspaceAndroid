package com.workspaceandroid.ui.screens.collection

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.UserCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
) : BaseViewModel<CollectionContract.Event, CollectionContract.State, CollectionContract.Effect>() {

    enum class SortType {
        AZ, DATE
    }

    init {
        fetchUserCollection()
    }

    private val allCollections: List<UserCollection>
        get() = viewState.value.allCollections

    override fun setInitialState(): CollectionContract.State =
        CollectionContract.State(isLoading = true)

    override fun handleEvents(event: CollectionContract.Event) {
        when (event) {
            is CollectionContract.Event.AddButtonClicked -> fetchUserCollection()
            is CollectionContract.Event.OnItemSelected -> updateExpandedCards(event.selectedPhrase)
            is CollectionContract.Event.OnSearchInput -> filterPhrases(event.searchText)
            is CollectionContract.Event.OnPhraseRemove -> removePhrase(event.phraseId)
            is CollectionContract.Event.OnPhraseReset -> resetPhrase(event.phraseId)
        }
    }

    private fun updateExpandedCards(selectedPhrase: Phrase) {
        setState {
            val updatedCards = viewState.value.phrases.map { //TODO refactor
                if (it.id == selectedPhrase.id) it.copy(isExpanded = !it.isExpanded)
                else it
            }.toMutableList()
            copy(phrases = updatedCards)
        }
    }

    private fun filterPhrases(searchInput: String) {
//        setState {
//            copy(phrases = userPhrases.filter { it.text.contains(searchInput) })
//        }
    }

    fun onCollectionSelected(collection: UserCollection) {
        val phrasesFromCollection = allCollections.firstOrNull { it.id == collection.id }?.phrases
        setState { copy(phrases = phrasesFromCollection ?: emptyList()) }
    }

    private fun fetchUserCollection() {
        viewModelScope.launch {
            val allCollections = collectionInteractor.getUserCollections()
            setState {
                copy(
                    allCollections = allCollections,
                    phrases = allCollections.flatMap { it.phrases },
                    isLoading = false
                )
            }
        }
    }

    private fun removePhrase(phraseId: Long) {
        viewModelScope.launch {
            collectionInteractor.removePhrase(phraseId)
        }
        val phrasesWithoutDeleted = allCollections
            .flatMap { it.phrases }
            .filter { it.id != phraseId }
        setState {
            copy(phrases = phrasesWithoutDeleted, isLoading = false)
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
                copy(phrases = updatedPhrases)
            }
        }
    }

    private fun List<Phrase>.sortByDate(): List<Phrase> = this.sortedByDescending { it.createdAt }

}