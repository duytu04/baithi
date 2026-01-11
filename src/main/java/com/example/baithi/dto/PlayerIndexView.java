package com.example.baithi.dto;

public class PlayerIndexView {
    private final long id;
    private final String playerName;
    private final int age;
    private final String indexName;
    private final double value;
    private final double min;
    private final double max;

    public PlayerIndexView(long id, String playerName, int age, String indexName, double value, double min, double max) {
        this.id = id;
        this.playerName = playerName;
        this.age = age;
        this.indexName = indexName;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getAge() {
        return age;
    }

    public String getIndexName() {
        return indexName;
    }

    public double getValue() {
        return value;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
