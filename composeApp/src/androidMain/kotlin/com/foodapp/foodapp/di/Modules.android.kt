package com.foodapp.foodapp.di

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.foodapp.foodapp.location.AndroidLocationInterface
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import com.foodapp.foodapp.presentation.location.LocationInterface
import com.foodapp.foodapp.screensize.ScreenSize
import com.foodapp.foodapp.storage.AndroidTokenStorage
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }
actual fun createTokenStorageModule(): Module = module {
    single<TokenStorage> { AndroidTokenStorage(androidContext()) }
}
actual fun getPlatformConfiguration(): Module = module {
        single<PlatformConfiguration> { ScreenSize(androidContext()) }
    }
actual fun getLocationModule():Module = module {
    single<LocationInterface> { AndroidLocationInterface() }
}
@Composable
actual fun PickImage(onImageSelected: (Pair<ImageBitmap, ByteArray>) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return@rememberLauncherForActivityResult
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
            onImageSelected(Pair(bitmap, bytes))
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch("image/*")
    }
}
