package nl.cinqict;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class ItemListController {

    Set<AnswerTime> itemList = new HashSet<>();

    // unfortunately the request parameter is there for testing the time limit
    @GetMapping
    public String get(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime currentTime) {
        return itemList.stream()
                .filter(shouldBeOlderThanTwoMinutes(currentTime))
                .sorted()
                .map(AnswerTime::getAnswer)
                .collect(Collectors.toList()).toString();
    }

    private Predicate<AnswerTime> shouldBeOlderThanTwoMinutes(LocalDateTime currentTime) {

        // need an effective final variable to be able to use a lambda
        final LocalDateTime now = (currentTime != null) ? currentTime : LocalDateTime.now();
        return at -> at.getTime().isBefore(now.minusMinutes(2));
    }

    @PostMapping("/post")
    public void post(@RequestBody String body) {
        System.out.println("Received: " + body);
        itemList.add(new AnswerTime(body));
    }

    @PostMapping("/reset")
    public void reset() {
        itemList.clear();
    }

}
