package nl.cinqict;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ItemListController {

    Set<String> itemList = new HashSet<>();

    @GetMapping
    public String get() {
        return itemList.toString();
    }

    @PostMapping("/post")
    public void post(@RequestBody String body) {
        System.out.println("Received: " + body);
        itemList.add(body);
    }

    @PostMapping("/reset")
    public void reset() {
        itemList.clear();
    }

}
