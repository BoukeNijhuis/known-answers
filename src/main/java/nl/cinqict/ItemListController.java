package nl.cinqict;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class ItemListController {

    List<AnswerTime> itemList = new ArrayList<>();

    // unfortunately the request parameter is there for testing the time limit
    @GetMapping
    public String get(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime currentTime) {
//        return itemList.stream()
//                .filter(shouldBeOlderThanGivenMinutes(currentTime, itemList.size()))
//                .sorted()
//                .map(AnswerTime::getAnswer)
//                .collect(Collectors.toList()).toString();
        final LocalDateTime now = (currentTime != null) ? currentTime : LocalDateTime.now();
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            AnswerTime current = itemList.get(i);
            if (current.getTime().isBefore(now.minusMinutes(i))) {
                resultList.add(current.getAnswer());
            }
        }
        return resultList.toString();
    }

    private Predicate<AnswerTime> shouldBeOlderThanGivenMinutes(LocalDateTime currentTime, int minutes) {

        // need an effective final variable to be able to use a lambda
        final LocalDateTime now = (currentTime != null) ? currentTime : LocalDateTime.now();
        return at -> at.getTime().isBefore(now.minusMinutes(minutes));
    }

    @PostMapping("/post")
    public void post(@RequestBody String body) {
        System.out.println("Received: " + body);
        AnswerTime answerTime = new AnswerTime(body);
        if (!itemList.contains(answerTime)) {
            itemList.add(answerTime);
        }
    }

    @PostMapping("/reset")
    public void reset() {
        itemList.clear();
    }

}
