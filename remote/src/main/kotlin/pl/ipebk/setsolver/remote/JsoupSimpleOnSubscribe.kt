package pl.ipebk.setsolver.remote

import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

/**
 * Implementation of [SingleOnSubscribe] that emits response [Document] from given url
 */
internal class JsoupSimpleOnSubscribe(private val url: String) : SingleOnSubscribe<Document> {
  override fun subscribe(emitter: SingleEmitter<Document>) {
    try {
      val response = Jsoup.connect(url).get()
      if (!emitter.isDisposed)
        emitter.onSuccess(response)
    } catch (ex: IOException) {
      if (!emitter.isDisposed)
        emitter.onError(ex)
    }
  }
}