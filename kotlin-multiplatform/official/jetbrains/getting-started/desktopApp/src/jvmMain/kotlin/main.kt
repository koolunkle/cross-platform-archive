import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.koolunkle.gettingstarted.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Getting Started") {
        App()
    }
}
