package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
  @RequestMapping(path = "/", method = RequestMethod.GET)
  public @ResponseBody
  String greeting() {
    return "Hello, world";
  }
}
