package com.workspaceandroid.ui.feature.home

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.PhraseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor
) : BaseViewModel<HomeContract.Event, HomeContract.HomeState, HomeContract.Effect>() {

    private var gamePhraseModels: List<PhraseModel> = emptyList()

    override fun setInitialState(): HomeContract.HomeState = HomeContract.HomeState.Loading

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.onButtonClicked -> TODO()
        }
    }

    init {
        fetchNewPhrasesForSwipeWidget()
    }

    fun fetchNewPhrasesForSwipeWidget() {
        viewModelScope.launch {
            gamePhraseModels = collectionInteractor.getPhrasesForGame()
            setState { HomeContract.HomeState.Success(gamePhraseModels) }
        }
    }

    fun onPositiveCLick(phraseId: Long) {
        sendPhraseStatus(phraseId, true)
    }

    fun onNegativeClick(phraseId: Long) {
        sendPhraseStatus(phraseId, false)
    }

    private fun sendPhraseStatus(phraseId: Long, isKnown: Boolean) {
        viewModelScope.launch {
            gamePhraseModels = gamePhraseModels.filter { it.id != phraseId }
            collectionInteractor.updatePhraseRepeatStatus(phraseId, isKnown)
            setState { HomeContract.HomeState.Success(gamePhraseModels) }
        }
    }

}