package nl.cinqict;

import java.time.LocalDateTime;
import java.util.Objects;

public class AnswerTime implements Comparable<AnswerTime> {

    private String answer;
    private LocalDateTime time;

    public AnswerTime(String answer) {
        this.answer = answer;
        this.time = LocalDateTime.now();
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getTime() {
        return time;
    }

    // necessary for sorting
    @Override
    public int compareTo(AnswerTime o) {
        if (o == null) return 1;

        return this.getTime().compareTo(o.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerTime that = (AnswerTime) o;
        return answer.equals(that.answer);
    }

    // necessary to prevent duplicate answers in a set
    @Override
    public int hashCode() {
        return Objects.hash(answer);
    }
}
