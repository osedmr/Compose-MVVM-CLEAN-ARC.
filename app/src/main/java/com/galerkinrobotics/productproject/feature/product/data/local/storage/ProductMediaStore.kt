package com.galerkinrobotics.productproject.feature.product.data.local.storage

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Galeriden gelen [content] URI'ları uygulama içi dosyaya kopyalar.
 * Böylece uygulama kapatılınca izin/bitmiş URI sorunu olmaz; Room'da saklanan yol kalıcıdır.
 */
@Singleton
class ProductMediaStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    suspend fun persistUriString(uriString: String): String = withContext(Dispatchers.IO) {
        if (uriString.isBlank()) return@withContext ""
        val existing = File(uriString)
        if (existing.isFile && existing.exists()) return@withContext existing.absolutePath
        val uri = Uri.parse(uriString)
        if (uri.scheme.equals("content", ignoreCase = true)) {
            return@withContext copyContentUriToInternalFile(uri)
        }
        uriString
    }

    private fun copyContentUriToInternalFile(source: Uri): String {
        val resolver = context.contentResolver
        val mime = resolver.getType(source) ?: "image/jpeg"
        val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime)
            ?: when {
                mime.contains("png", ignoreCase = true) -> "png"
                mime.contains("webp", ignoreCase = true) -> "webp"
                else -> "jpg"
            }
        val dir = File(context.filesDir, PRODUCT_MEDIA_DIR).apply { mkdirs() }
        val outFile = File(dir, "${UUID.randomUUID()}.$ext")
        resolver.openInputStream(source)?.use { input ->
            outFile.outputStream().use { output -> input.copyTo(output) }
        } ?: return source.toString()

        return outFile.absolutePath
    }

    companion object {
        private const val PRODUCT_MEDIA_DIR = "product_media"
    }
}
