package com.zadanie2;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("fire -> grass");
        new DealDamage("fire", "grass");
        System.out.println("fighting -> ice rock");
        new DealDamage("fighting", "ice");
        System.out.println("psychic -> poison dark");
        new DealDamage("psychic", "dark");
        System.out.println("water -> normal");
        new DealDamage("water", "normal");
        System.out.println("fire -> rock");
        new DealDamage("fire", "rock");
    }
}
