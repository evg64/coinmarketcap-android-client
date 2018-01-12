package com.eugene.cmcclient.ui.main

import com.eugene.cmcclient.ui.common.BaseMvpPresenter
import javax.inject.Inject

/**
 * Created by Eugene on 09.12.2017.
 */
class PresenterMainActivity @Inject constructor(): BaseMvpPresenter<MvpActivityMain.View>(),
                                                   MvpActivityMain.Presenter {
}