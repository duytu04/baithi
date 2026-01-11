package com.example.baithi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "player_indexes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"player_id", "index_id"})
)
public class PlayerIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "index_id", nullable = false)
    private PerformanceIndex index;

    @Column(name = "index_value", nullable = false)
    private double value;

    protected PlayerIndex() {
    }

    public PlayerIndex(Player player, PerformanceIndex index, double value) {
        this.player = player;
        this.index = index;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public PerformanceIndex getIndex() {
        return index;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
