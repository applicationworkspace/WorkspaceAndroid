import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.repositories.ICollectionRepository
import defaultModels.defaultPhraseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserCollectionInteractorTest {

    @Mock
    private lateinit var collectionRepository: ICollectionRepository

    @InjectMocks
    private lateinit var collectionInteractor: CollectionInteractor

    @Test
    fun `get user phrases`() = runBlocking {
        val phrases: List<PhraseModel> = listOf(
            defaultPhraseModel(),
            defaultPhraseModel(),
            defaultPhraseModel()
        )

        //given
        whenever(collectionRepository.fetchUserPhrases()).thenReturn(phrases)

        //when
        val resultModel = collectionInteractor.getUserPhrases()

        //then
        assertEquals(phrases, resultModel)
    }

}