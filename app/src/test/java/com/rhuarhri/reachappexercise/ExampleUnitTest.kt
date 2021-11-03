package com.rhuarhri.reachappexercise

import androidx.lifecycle.MutableLiveData
import com.rhuarhri.reachappexercise.online.Online
import com.rhuarhri.reachappexercise.online.json.ProductList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

@RunWith(MockitoJUnitRunner::class)
class TestOnline {

    @Mock
    private lateinit var mockOnline : Online

    @Test
    fun notAccess() = runBlocking {

        `when`(mockOnline.getProducts()).then {
            val testValue : ProductList? = null
            mockOnline.currentProductList.value = testValue
            it
        }

        val testRepo = MainActivityRepository(mockOnline)

        testRepo.productListLiveData.observeForever {  }

        testRepo.getProducts()

        assertEquals("test what happens if there is no internet", true,
            testRepo.productListLiveData.value!!.isEmpty())

    }

    @Test
    fun withAccess() {

    }

}