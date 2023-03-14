package com.xx.entity;

public class Cook {
    private String id;
    private String name;
    private String img;
    private String time;
    private String people;
    private String zhaiyao;
    private String buzhou;

    @Override
    public String toString() {
        return "Cook{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", time='" + time + '\'' +
                ", people='" + people + '\'' +
                ", zhaiyao='" + zhaiyao + '\'' +
                ", buzhou='" + buzhou + '\'' +
                '}';
    }

    public Cook() {
    }

    public Cook(String id, String name, String img, String time, String people, String zhaiyao, String buzhou) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.time = time;
        this.people = people;
        this.zhaiyao = zhaiyao;
        this.buzhou = buzhou;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getZhaiyao() {
        return zhaiyao;
    }

    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    public String getBuzhou() {
        return buzhou;
    }

    public void setBuzhou(String buzhou) {
        this.buzhou = buzhou;
    }
}
