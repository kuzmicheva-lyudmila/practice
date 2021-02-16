package ru.kl.model;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Value
@Builder
public class LombokModel {

    int id;

    String name;

    List<String> list;

    private LombokModel(int id, String name, List<String> list) {
        this.id = id;
        this.name = name;
        this.list = list != null ? new ArrayList<>(list) : Collections.emptyList();
    }

    public List<String> getList() {
        return Collections.unmodifiableList(list);
    }
}
