package com.example.mangaworld.object;

import java.io.Serializable;

public class Chap implements Serializable {
    private int numberChap;

    public Chap(int numberChap) {
        this.numberChap = numberChap;
    }

    public int getNumberChap() {
        return numberChap;
    }

}
