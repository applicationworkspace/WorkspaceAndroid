package com.workspaceandroid.data.repository

import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.data.repositories.CollectionRepository
import com.workspaceandroid.data.defaultTestModels.defaultPhraseModel
import com.workspaceandroid.data.defaultTestModels.defaultPhraseNetDTOModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserCollectionRepositoryTest {

    @Mock
    lateinit var netSource: IPhrasesNetSource
    @Mock
    lateinit var phrasesNetMapper: PhrasesNetMapper

    @InjectMocks
    private lateinit var collectionRepository: CollectionRepository

    @Test
    fun `should load user phrases`() = runBlocking {
        //given

        val phrasesNetDTO = listOf(defaultPhraseNetDTOModel(), defaultPhraseNetDTOModel())
        val phrasesDomain = listOf(defaultPhraseModel(), defaultPhraseModel())

        whenever(netSource.fetchUserPhrases()).thenReturn(phrasesNetDTO)
        whenever(phrasesNetMapper.fromEntityList(phrasesNetDTO)).thenReturn(phrasesDomain)

        //when
        val phrasesResult = collectionRepository.fetchUserPhrases()

        //then
        assertEquals(phrasesDomain, phrasesResult)
//        assertEquals(true, false)
    }

//    verify(dbSource).insertOrUpdatePhrase(DbDTO)

//    phrasesResult.test {
//        assertEquals(phrasesModel, awaitItem())
//        awaitComplete()
//    }

//    whenever(dbSource.getAllPhrasesFlow()).thenReturn(
//    flow {
//        emit(dbPhrases)
//    }
//    )

}