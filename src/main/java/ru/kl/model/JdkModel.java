package ru.kl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JdkModel {

    private final int id;

    private final String name;

    private final List<String> list;

    private JdkModel(int id, String name, List<String> list) {
        this.id = id;
        this.name = name;
        this.list = list != null ? new ArrayList<>(list) : Collections.emptyList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getList() {
        return Collections.unmodifiableList(list);
    }

    public static JdkModelBuilder builder() {
        return new JdkModelBuilder();
    }

    public static class JdkModelBuilder {

        private int id;

        private String name;

        private List<String> list;

        private JdkModelBuilder(){
        }

        public JdkModelBuilder id(int id) {
            this.id = id;
            return this;
        }

        public JdkModelBuilder name(String name) {
            this.name = name;
            return this;
        }

        public JdkModelBuilder list(List<String> list) {
            this.list = list;
            return this;
        }

        public JdkModel build() {
            return new JdkModel(id, name, list);
        }
    }
}
