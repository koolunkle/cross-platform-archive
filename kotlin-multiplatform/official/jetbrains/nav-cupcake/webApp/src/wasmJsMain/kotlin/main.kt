import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.koolunkle.cupcake.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport("composeApp") { App() }
}
