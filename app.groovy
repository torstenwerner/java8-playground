import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class App {
    @RequestMapping("/")
    def hi() { "Hello, world!" }
}