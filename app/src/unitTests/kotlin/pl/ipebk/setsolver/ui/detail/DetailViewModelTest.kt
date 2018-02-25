package pl.ipebk.setsolver.ui.detail

import pl.ipebk.setsolver.data.remote.model.Owner
import pl.ipebk.setsolver.data.remote.model.Repo
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    lateinit var owner: Owner
    lateinit var repo: Repo
    lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        owner = Owner("Author",
                "someURL")

        repo = Repo("Name",
                "Author/Name",
                owner,
                "Some random repo",
                50,
                100)

        viewModel = DetailViewModel(repo)
        viewModel.bind()
    }

    @Test
    fun getName_returnsCorrectName() {
        Assert.assertEquals(viewModel.getName(), repo.fullName)
    }

    @Test
    fun getDescription_returnsCorrectDescription() {
        Assert.assertEquals(viewModel.getDescription(), repo.description)
    }

    @Test
    fun getStars_returnsCorrectStarCount() {
        Assert.assertEquals(viewModel.getStars(), repo.stars.toString())
    }

    @Test
    fun getForks_returnsCorrectForkCount() {
        Assert.assertEquals(viewModel.getForks(), repo.forks.toString())
    }

    @Test
    fun getAvatarURL_returnsCorrectString() {
        Assert.assertEquals(viewModel.getAvatarURL(), repo.owner.avatarUrl)
    }
}