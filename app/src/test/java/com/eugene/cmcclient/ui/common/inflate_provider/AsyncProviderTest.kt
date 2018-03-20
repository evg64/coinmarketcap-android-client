package com.eugene.cmcclient.ui.common.inflate_provider

import android.test.mock.MockContext
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Created by Eugene on 25.02.2018.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(Log::class)
class AsyncProviderTest {
    private val context = MockContext()
    private val inflater: LayoutInflater = mock<LayoutInflater>(LayoutInflater::class.java)
    private val parent: ViewGroup = mock<ViewGroup>(ViewGroup::class.java)

    companion object {
        const val DEFAULT_CACHE_SIZE = 5
    }

    private fun newProvider() : InflatedViewsProviderAsync {
        return newProvider(DEFAULT_CACHE_SIZE)
    }

    private fun newProvider(cacheSize: Int) : InflatedViewsProviderAsync {
        val viewTypes = mapOf(0 to 0)
        val cacheSizes = mapOf(0 to cacheSize)
        return InflatedViewsProviderAsync(viewTypes, cacheSizes)
    }

    @Before fun setUp() {
        PowerMockito.mockStatic(Log::class.java)
        `when`(inflater.inflate(0, null, false)).thenReturn(View(context))
    }

    @Test fun testCacheSetup() {
        val providerAsync = newProvider()
        providerAsync.setupCacheAsync(inflater, null)
    }

    @Test fun testGetView() {
        val providerAsync = newProvider()
        providerAsync.setupCacheAsync(inflater, null)
        Thread.sleep(150)
        Assert.assertNotNull(providerAsync.getView(inflater, parent, 0))
    }

    @Test fun testCleanupViews() {
        val providerAsync = newProvider(80)
        providerAsync.setupCacheAsync(inflater, null)
        Thread.sleep(150)
        providerAsync.cleanupViews()
        Thread.sleep(150)
        Assert.assertNull(providerAsync.cache.pop(0))
    }
}