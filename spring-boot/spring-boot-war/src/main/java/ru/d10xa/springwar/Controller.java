package ru.d10xa.springwar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/ctrl")
public class Controller {

   @RequestMapping
   public Map map(){
      Map m = new LinkedHashMap();
      m.put("abc","xyz");
      return m;
   }

}