package com.eugene.cmcclient.ui.common.formatters

import android.content.res.Resources
import com.eugene.cmcclient.di.Injector

/**
 * Created by Eugene on 18.01.2018.
 */
abstract class FormatterWithRes<T> : Formatter<T>{
    protected val res: Resources by lazy { Injector.componentApp.getRes() }
}