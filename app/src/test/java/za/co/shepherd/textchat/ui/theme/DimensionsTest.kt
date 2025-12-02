package za.co.shepherd.textchat.ui.theme

import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

class DimensionsTest {
    @Test
    fun dimensions_areCorrect() {
        assertEquals(4.dp, Dimensions.smallerPadding)
        assertEquals(8.dp, Dimensions.smallPadding)
        assertEquals(12.dp, Dimensions.smallMediumPadding)
        assertEquals(16.dp, Dimensions.mediumPadding)
        assertEquals(24.dp, Dimensions.largePadding)
        assertEquals(48.dp, Dimensions.largeXPadding)
        assertEquals(300.dp, Dimensions.maxWidth)
    }
}