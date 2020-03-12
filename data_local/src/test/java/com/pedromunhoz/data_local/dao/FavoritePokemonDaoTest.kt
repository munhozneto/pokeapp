package com.pedromunhoz.data_local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.pedromunhoz.data_local.db.PokeDatabase
import com.pedromunhoz.data_local.test.EntityDataFactory
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FavoritePokemonDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var db: PokeDatabase
    private lateinit var favoritePokemonDao: FavoritePokemonDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.applicationContext,
                PokeDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()

        favoritePokemonDao = db.favoritePokemonDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `should insert data with success`() {
        val favoritePokemonEntity = EntityDataFactory.makeFavoritePokemon()

        favoritePokemonDao.insert(favoritePokemonEntity).test()

        val hasPokeFavorite = favoritePokemonDao.hasPokeFavorite(favoritePokemonEntity.id).blockingGet()

        Assert.assertTrue(hasPokeFavorite)
    }

    @Test
    fun `should delete data with success`() {
        val favoritePokemonEntity = EntityDataFactory.makeFavoritePokemon()

        favoritePokemonDao.insert(favoritePokemonEntity).test()

        favoritePokemonDao.delete(favoritePokemonEntity.id).test()

        val hasPokeFavorite = favoritePokemonDao.hasPokeFavorite(favoritePokemonEntity.id).blockingGet()

        Assert.assertFalse(hasPokeFavorite)
    }

    @Test
    fun `should list favorites`() {
        val favoritePokemonList = EntityDataFactory.makeFavoritePokemonList(3)

        favoritePokemonList.forEach {
            favoritePokemonDao.insert(it).test()
        }

        val favoriteFromDatabaseList = favoritePokemonDao.getPokeFavorites().blockingGet()

        Assert.assertEquals(favoritePokemonList.size, favoriteFromDatabaseList.size)
    }
}