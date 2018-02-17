package com.eugene.cmcclient.ui.common.formatters

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Eugene on 23.01.2018.
 */
class CirculatingSupplyFormatterTest {

    val formatter: CirculatingSupplyFormatter = CirculatingSupplyFormatter()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testIntegerFormat() {
        val result = formatter.format(123456789.0)
        assertEquals("123456789", result)
    }

    @Test
    fun testDecimalFormatSingleDigit() {
        val result = formatter.format(123456789.5)
        assertEquals("123456789.5", result)
    }

    @Test
    fun testDecimalFormat2Digit() {
        val result = formatter.format(123456789.55)
        assertEquals("123456789.55", result)
    }

    @Test
    fun testDecimalFormat3Digit() {
        val result = formatter.format(123456789.555)
        assertEquals("123456789.56", result)
    }

}