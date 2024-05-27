package org.example.model;

import lombok.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private int id;
    private String text;
    private Set<String> labels;

    public Note(String text, Set<String> labels) {
        this.id = idGenerator.incrementAndGet();
        this.text = text;
        this.labels = labels.stream().map(String::toUpperCase).collect(Collectors.toSet());
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels.stream().map(String::toUpperCase).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return id + "#" + text + "\n" + String.join(";", labels);
    }
}