package org.totschnig.ocr

import android.content.Context
import android.content.Intent
import androidx.annotation.Keep
import org.totschnig.myexpenses.MyApplication
import org.totschnig.myexpenses.feature.OcrFeature.Companion.intent
import org.totschnig.myexpenses.feature.OcrResult
import org.totschnig.myexpenses.preference.PrefHandler
import java.io.File
import javax.inject.Inject

@Keep
class OcrHandlerImpl @Inject constructor(prefHandler: PrefHandler, application: MyApplication) : AbstractOcrHandlerImpl(prefHandler, application) {
    override suspend fun handleData(intent: Intent) = (intent.getParcelableExtra("result") as? Text)?.let {
        processTextRecognitionResult(it, queryPayees())
    } ?: throw IllegalArgumentException("Unable to retrieve result from intent")

    override fun info(context: Context): CharSequence =
            intent().resolveActivity(context.packageManager).toShortString()

    override suspend fun runTextRecognition(file: File, context: Context): OcrResult {
        throw IllegalStateException()
    }
}