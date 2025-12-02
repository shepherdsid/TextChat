package za.co.shepherd.textchat.ui.theme

import android.os.Build
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.N], manifest = Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class ThemeTest {

    private lateinit var controller: ActivityController<ComponentActivity>
    private lateinit var activity: ComponentActivity

    @Before
    fun setUp() {
        controller = Robolectric.buildActivity(ComponentActivity::class.java).setup()
        activity = controller.get()
    }

    @After
    fun tearDown() {
        controller.pause().stop().destroy()
    }

    private fun renderTheme(dark: Boolean, dynamic: Boolean): ColorScheme {
        var scheme: ColorScheme? = null
        activity.setContent {
            TextChatTheme(darkTheme = dark, dynamicColor = dynamic) {
                scheme = MaterialTheme.colorScheme
            }
        }
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        return scheme!!
    }

    @Test
    fun lightTheme_staticColors_applied_and_statusBarColorSet() {
        val cs = renderTheme(dark = false, dynamic = false)
        assertEquals(LightBackground, cs.background)
        assertEquals(LightSurface, cs.surface)
        assertEquals(OnPrimaryLight, cs.onPrimary)
        assertEquals(Purple40, cs.primary)
        assertEquals(PrimaryContainerLight, cs.primaryContainer)
        assertEquals(SurfaceVariantLight, cs.surfaceVariant)
        assertEquals(cs.primary.toArgb(), activity.window.statusBarColor)
    }

    @Test
    fun darkTheme_staticColors_applied_and_statusBarColorSet() {
        val cs = renderTheme(dark = true, dynamic = false)
        assertEquals(DarkBackground, cs.background)
        assertEquals(DarkSurface, cs.surface)
        assertEquals(OnPrimaryDark, cs.onPrimary)
        assertEquals(Purple80, cs.primary)
        assertEquals(PrimaryContainerDark, cs.primaryContainer)
        assertEquals(SurfaceVariantDark, cs.surfaceVariant)
        assertEquals(cs.primary.toArgb(), activity.window.statusBarColor)
    }

    @Test
    fun dynamicColor_preS_fallsBackToStatic_light() {
        val cs = renderTheme(dark = false, dynamic = true)
        assertEquals(LightBackground, cs.background)
        assertEquals(LightSurface, cs.surface)
        assertEquals(Purple40, cs.primary)
    }
}