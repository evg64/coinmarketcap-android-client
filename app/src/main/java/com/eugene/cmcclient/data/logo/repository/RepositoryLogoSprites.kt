package com.eugene.cmcclient.data.logo.repository

import android.graphics.*
import android.util.Log
import com.eugene.cmcclient.R
import com.eugene.cmcclient.data.BackendLogoCSS
import com.eugene.cmcclient.data.tickers.model.StringId
import com.eugene.cmcclient.di.Injector
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.rxjava2.filterSome
import com.gojuno.koptional.toOptional
import com.osbcp.cssparser.CSSParser
import com.osbcp.cssparser.PropertyValue
import com.osbcp.cssparser.Rule
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.displayMetrics
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Takes single bitmap with logo sprites, splits it and saves to separate files on disk.
 *
 * Provides map with currency names and paths to logos.
 *
 * Created by Eugene on 13.01.2018.
 */
class RepositoryLogoSprites(
        private val logosBackend: BackendLogoCSS,
        private val picasso: Picasso
) : RepositoryLogo {
    private var map: Map<StringId, Bitmap>? = null

    companion object {
        private val PATTERN_STYLENAME = Pattern.compile("\\.s-s-(\\w+)")
        private val PATTERN_POSITION = Pattern.compile("(-?\\d+)(px)? (-?\\d+)(px)?")
        private val PATTERN_WIDTH_HEIGHT = Pattern.compile("(\\d+)px")
        val BITMAP_SCALE_FACTOR: Float = Injector.componentApp.getAppContext().displayMetrics.density
    }

    private data class LogoInBitmap(val tickerId: StringId, val position: Point, val width: Int, val height: Int)
    private data class TickerIdWithBitmap(val tickerId: StringId, val logo: Bitmap)

//    override fun getLogoMap(): Observable<Map<Name, URI>> {
//        return Observable.just(mapOf<Name, URI>())
//    }
    private var singleBitmap: Bitmap? = null

    override fun getLogoMap(): Observable<Map<StringId, Bitmap>> {
        if (map == null) {
            val bitmapLoading = if (singleBitmap == null) {
                Observable.create(ObservableOnSubscribe<Bitmap> {
                    val bmp: Bitmap?
                    try {
//                        bmp = picasso.load(DataConstants.PATH_LOGO_SINGLE_BITMAP).get()
                        bmp = picasso.load(R.drawable.currencies).get()
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
                    .map {
                        listOf<Rule>()
//                        CSSParser.parse(it.string())
                    }
                    .flatMapIterable { it }
                    .filter { rule: Rule -> rule.selectors.size == 1 }
                    .map { rule: Rule ->
                        val matcher = PATTERN_STYLENAME.matcher(rule.selectors[0].toString())
                        Pair<List<PropertyValue>, Matcher>(rule.propertyValues, matcher)
                    }
                    .filter { it.second.matches() }
                    .map { pair: Pair<List<PropertyValue>, Matcher> ->
                        computeLogoInBitmap(pair.first, pair.second)
                    }
                    .filterSome()
                    .doOnNext{ Log.d("DATA", "LogoInfo: $it")}
                    .subscribeOn(Schedulers.computation())

            return Observable.combineLatest(
                    bitmapLoading,
                    cssRulesLoading,
                    BiFunction { bitmap: Bitmap, logoInfo: LogoInBitmap -> Pair(bitmap, logoInfo) })
                    .map { toTickerIdWithBitmap(it.first, it.second) }
                    .filterSome()
                    .toList()
                    .map { names: List<TickerIdWithBitmap> ->
                        val tmp: MutableMap<StringId, Bitmap> = HashMap()
                        names.forEach { tmp[it.tickerId] = it.logo }
                        val result: Map<StringId, Bitmap> = HashMap(tmp)
                        result
                    }
                    .doOnSuccess{ map = it }
                    .toObservable()
                    .onErrorResumeNext(Observable.just(mapOf()))
                    .subscribeOn(Schedulers.computation())
        } else {
            return Observable.just(map)
        }
    }

    private fun toTickerIdWithBitmap(bitmap: Bitmap, logoInfo: LogoInBitmap): Optional<TickerIdWithBitmap> {
        val resultBitmap: Bitmap = Bitmap.createBitmap(
                (logoInfo.width * BITMAP_SCALE_FACTOR).toInt(),
                (logoInfo.height * BITMAP_SCALE_FACTOR).toInt(),
                Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(resultBitmap)
        val srcRect = Rect(
                logoInfo.position.x,
                logoInfo.position.y,
                logoInfo.position.x + logoInfo.width,
                logoInfo.position.y + logoInfo.height
        )
        canvas.drawBitmap(bitmap,
                          srcRect,
                          RectF(0F,
                                0F,
                                logoInfo.width * BITMAP_SCALE_FACTOR,
                                logoInfo.height * BITMAP_SCALE_FACTOR),
                          null
        )

        return TickerIdWithBitmap(logoInfo.tickerId, resultBitmap).toOptional()
    }

    private fun computeLogoInBitmap(first: List<PropertyValue>, second: Matcher): Optional<LogoInBitmap> {
        val id = StringId(second.group(1))
        var position: Point? = null
        var width: Int? = null
        var height: Int? = null

        for (propertyValue in first) {
            try {
                when (propertyValue.property) {
                    "background-position" -> {
                        val matcher = PATTERN_POSITION.matcher(propertyValue.value)
                        if (matcher.matches()) {
                            // if we multiply width and height by (-1), we get position of left-top corner of icon
                            // where (0,0) is left-top corner of the whole image
                            val w: Int = -1 * matcher.group(1).toInt()
                            val h: Int = -1 * matcher.group(3).toInt()
                            position = Point(w, h)
                        }
                    }
                    "width" -> {
                        val matcher = PATTERN_WIDTH_HEIGHT.matcher(propertyValue.value)
                        if (matcher.matches()) {
                            width = matcher.group(1).toInt()
                        }
                    }
                    "height" -> {
                        val matcher = PATTERN_WIDTH_HEIGHT.matcher(propertyValue.value)
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
            LogoInBitmap(id, position, width, height).toOptional()
        }
    }
}