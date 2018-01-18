package com.eugene.cmcclient.di

import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Created by Eugene on 07.12.2017.
 */
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ScopeApp

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ScopeActivity

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ScopeFragment

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class AppContext

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FilesRoot