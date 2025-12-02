package za.co.shepherd.textchat.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorTest {
    @Test
    fun lightThemeColors_areCorrect() {
        assertEquals(Color(0xFF6200EE), Purple40)
        assertEquals(Color(0xFF625b71), PurpleGrey40)
        assertEquals(Color(0xFF7D5260), Pink40)
        assertEquals(Color(0xFFF7F7F7), LightBackground)
        assertEquals(Color(0xFFFFFFFF), LightSurface)
        assertEquals(Color.White, OnPrimaryLight)
        assertEquals(Color.White, OnSecondaryLight)
        assertEquals(Color.White, OnTertiaryLight)
        assertEquals(Color(0xFF1C1B1F), OnBackgroundLight)
        assertEquals(Color(0xFF1C1B1F), OnSurfaceLight)
        assertEquals(Color(0xFFBB86FC), PrimaryContainerLight)
        assertEquals(Color.Black, OnPrimaryContainerLight)
        assertEquals(Color(0xFFE0E0E0), SecondaryContainerLight)
        assertEquals(Color.Black, OnSecondaryContainerLight)
        assertEquals(Color(0xFFECECEC), SurfaceVariantLight)
    }

    @Test
    fun darkThemeColors_areCorrect() {
        assertEquals(Color(0xFFD0BCFF), Purple80)
        assertEquals(Color(0xFFCCC2DC), PurpleGrey80)
        assertEquals(Color(0xFFEFB8C8), Pink80)
        assertEquals(Color(0xFF121212), DarkBackground)
        assertEquals(Color(0xFF1E1E1E), DarkSurface)
        assertEquals(Color(0xFF3700B3), OnPrimaryDark)
        assertEquals(Color(0xFF3700B3), OnSecondaryDark)
        assertEquals(Color(0xFF3700B3), OnTertiaryDark)
        assertEquals(Color(0xFFE6E1E5), OnBackgroundDark)
        assertEquals(Color(0xFFE6E1E5), OnSurfaceDark)
        assertEquals(Color(0xFF3700B3), PrimaryContainerDark)
        assertEquals(Color(0xFFEADDFF), OnPrimaryContainerDark)
        assertEquals(Color(0xFF4A4458), SecondaryContainerDark)
        assertEquals(Color(0xFFE8DEF8), OnSecondaryContainerDark)
        assertEquals(Color(0xFF2C2C2C), SurfaceVariantDark)
    }
}