package com.workspaceandroid.ui.screens.add_phrase

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.PhraseInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPhraseViewModel @Inject constructor(private val collectionInteractor: CollectionInteractor) :
    BaseViewModel<AddPhraseContract.Event, AddPhraseContract.State, AddPhraseContract.Effect>() {

    private val userInputPhrase = PhraseInput()

    override fun setInitialState(): AddPhraseContract.State = AddPhraseContract.State().copy(isLoading = true)

    override fun handleEvents(event: AddPhraseContract.Event) {
        when (event) {
            is AddPhraseContract.Event.OnPhraseUpdated -> {
                event.phraseBuilder.invoke(userInputPhrase)
//                loadPhrasePrediction()
                setState { copy(selectedCollectionChip = userInputPhrase.selectedCollectionId) }
            }
            AddPhraseContract.Event.OnSaveButtonClicked -> {
                saveUserPhrase()
            }
        }
    }

    init {
        viewModelScope.launch {
            val fetchedCollections = collectionInteractor.getUserCollections()
                .map { Pair(it.id, it.name) }
            setState { copy(userCollections = fetchedCollections) }
        }
    }

    private fun saveUserPhrase() {
        viewModelScope.launch { collectionInteractor.addUserPhrase(userInputPhrase) }
    }

    private fun loadPhrasePrediction() {
        if (userInputPhrase.text.length > 2) {
            viewModelScope.launch {
                val predictedPhrase = collectionInteractor.getPhrasePrediction(userInputPhrase.text)
                setState { this.copy(predictedPhrase = predictedPhrase, isLoading = false) }
            }
        }
    }

}