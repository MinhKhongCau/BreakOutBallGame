package com;

public class Player extends Person {
    Integer score;
    Integer life;

    public Player() {
        super();
        this.score = 0;
        this.life = 3;
    }

    public Player(String name, Integer score, Integer life) {
        super("A01", name);
        this.score = score;
        this.life = life;
    }
    
    public Player(String name, Integer score) {
        super("A01", name);
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + super.getId() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", score='" + score + '\'' +
                ", life='" + life + '\'' +
                '}';
    }
}
