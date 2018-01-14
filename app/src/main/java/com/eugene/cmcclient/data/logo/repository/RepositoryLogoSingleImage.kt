package com.eugene.cmcclient.data.logo.repository

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import com.eugene.cmcclient.data.BackendLogoCSS
import com.eugene.cmcclient.data.DataConstants
import com.eugene.cmcclient.data.tickers.model.Name
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.gojuno.koptional.rxjava2.filterSome
import com.gojuno.koptional.toOptional
import com.osbcp.cssparser.CSSParser
import com.osbcp.cssparser.PropertyValue
import com.osbcp.cssparser.Rule
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.net.URI
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Takes single bitmap with logo sprites, splits it and saves to separate files on disk.
 *
 * Provides map with currency names and paths to logos.
 *
 * Created by Eugene on 13.01.2018.
 */
class RepositoryLogoSingleImage(
        private val logosBackend: BackendLogoCSS,
        private val picasso: Picasso
) : RepositoryLogo {
    private var map: Map<Name, Bitmap>? = null

    private val patternStylename = Pattern.compile("\\.s-s-(\\w+)")
    private val patternPosition = Pattern.compile("(-?\\d+)(px)? (-?\\d+)(px)?")
    private val patternWidthHeight = Pattern.compile("(\\d+)px")

    private data class LogoInBitmap(val name: String, val position: Point, val width: Int, val height: Int)
    private data class NameWithFilePathURI(val name: Name, val logo: Bitmap)

//    override fun getLogoMap(): Observable<Map<Name, URI>> {
//        return Observable.just(mapOf<Name, URI>())
//    }
    private var singleBitmap: Bitmap? = null

    override fun getLogoMap(): Observable<Map<Name, Bitmap>> {
        if (map == null) {
            val bitmapLoading = if (singleBitmap == null) {
                Observable.create(ObservableOnSubscribe<Bitmap> {
                    var bmp: Bitmap? = null
                    try {
                        bmp = picasso.load(DataConstants.PATH_LOGO_SINGLE_BITMAP).get()
                    } catch (e: Exception) {
                        it.onError(RuntimeException("Failed to load single bitmap with logos", e))
                        return@ObservableOnSubscribe
                    }
                    if (bmp == null) {
                        it.onError(NullPointerException("Failed to load single bitmap with logos"))
                    } else {
                        singleBitmap = bmp
                        it.onNext(bmp)
                        it.onComplete()
                    }
                })
                        .retry(2)
                        .subscribeOn(Schedulers.io())
            } else {
                Observable.just<Bitmap>(singleBitmap)
            }

            val cssRulesLoading = logosBackend.getLogosCSS()
                    .subscribeOn(Schedulers.io())
                    .map { CSSParser.parse(it.string()) }
                    .flatMapIterable { it }
                    .filter { rule: Rule -> rule.selectors.size == 1 }
                    .map { rule: Rule ->
                        val matcher = patternStylename.matcher(rule.selectors[0].toString())
                        Pair<List<PropertyValue>, Matcher>(rule.propertyValues, matcher)
                    }
                    .filter { it.second.matches() }
                    .map { pair: Pair<List<PropertyValue>, Matcher> ->
                        computeLogoInBitmap(pair.first, pair.second)
                    }
                    .filterSome()
                    .doOnNext{ Log.d("DATA", "LogoInfo: $it")}
                    .subscribeOn(Schedulers.computation())

//            return Observable.just(mapOf<Name, URI>())
            return Observable.combineLatest(
                    bitmapLoading,
                    cssRulesLoading,
                    BiFunction { bitmap: Bitmap, logoInfo: LogoInBitmap -> Pair(bitmap, logoInfo) })
                    .map { getNameWithFilePath(it.first, it.second) }
                    .filterSome()
//                    .map { NameWithFilePathURI(Name(it.name), URI.create(it.filepath)) }
                    .map { NameWithFilePathURI(Name(it.name), it.logo) }
                    .toList()
                    .map { names: List<NameWithFilePathURI> ->
                        val map: MutableMap<Name, Bitmap> = HashMap()
                        names.forEach { map[it.name] = it.logo }
                        map as Map<Name, Bitmap>
                    }
                    .doOnSuccess{ map = it }
                    .toObservable()
                    .onErrorResumeNext(Observable.just(mapOf()))
                    .subscribeOn(Schedulers.computation())
        } else {
            return Observable.just(map)
        }
    }

    data class NameWithFilePath(val name: String, val logo: Bitmap)

    var drawn = false

    private fun getNameWithFilePath(bitmap: Bitmap, logoInfo: LogoInBitmap): Optional<NameWithFilePath> {
        if (drawn) {
            return None
        } else {
            drawn = true
            val resultBitmap: Bitmap = Bitmap.createBitmap(logoInfo.width, logoInfo.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(resultBitmap)
            val srcRect = Rect(
                    logoInfo.position.x,
                    logoInfo.position.y,
                    logoInfo.position.x + logoInfo.width,
                    logoInfo.position.y + logoInfo.height
            )
            canvas.drawBitmap(bitmap,
                              srcRect,
                              Rect(0, 0, logoInfo.width, logoInfo.height),
                              null
            )
            return NameWithFilePath(logoInfo.name, resultBitmap).toOptional()
        }
    }

    private fun computeLogoInBitmap(first: List<PropertyValue>, second: Matcher): Optional<LogoInBitmap> {
        val name = second.group(1)
        var position: Point? = null
        var width: Int? = null
        var height: Int? = null

        for (propertyValue in first) {
            try {
                when (propertyValue.property) {
                    "background-position" -> {
                        val matcher = patternPosition.matcher(propertyValue.value)
                        if (matcher.matches()) {
                            val w: Int = matcher.group(1).toInt()
                            val h: Int = matcher.group(3).toInt()
                            position = Point(w, h)
                        }
                    }
                    "width" -> {
                        val matcher = patternWidthHeight.matcher(propertyValue.value)
                        if (matcher.matches()) {
                            width = matcher.group(1).toInt()
                        }
                    }
                    "height" -> {
                        val matcher = patternWidthHeight.matcher(propertyValue.value)
                        if (matcher.matches()) {
                            height = matcher.group(1).toInt()
                        }
                    }
                }
            } catch (e: Exception) {
                // if we fail to regexp something, we just won't fill some of position, width, height with value
                // so we will probably have fewer logos in our resulting map
                break
            }
        }

        return if (position == null || width == null || height == null) {
            None
        } else {
            LogoInBitmap(name, position, width, height).toOptional()
        }
    }
}