package dev.gmarques.bancodedados.data.room.dao

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.OrderWith
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Alphanumeric
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@OrderWith(Alphanumeric::class)
class TesteDeTest : TestCase() {

    var x = 0

    @Test
    fun testeA() {
        Log.d("USUK", "TesteDeTest.testeA: x: $x")
        x += 1
        Log.d("USUK", "TesteDeTest.testeA: x: $x")
    }

    @Test
    fun testeB() {
        Log.d("USUK", "TesteDeTest.testeB: x: $x")
        assertTrue(x == 1)
        Log.d("USUK", "TesteDeTest.testeB: x: $x")
    }

    @Test
    fun testeC() {
        Log.d("USUK", "TesteDeTest.testeC: x: $x")
        x += 1
        Log.d("USUK", "TesteDeTest.testeC: x: $x")
    }

    @Test
    fun testeD() {
        Log.d("USUK", "TesteDeTest.testeD: x: $x")
        assertTrue(x == 2)
        Log.d("USUK", "TesteDeTest.testeD: x: $x")
    }
}