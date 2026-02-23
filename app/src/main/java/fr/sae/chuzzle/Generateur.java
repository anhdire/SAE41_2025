package fr.sae.chuzzle;

import java.util.Random;

public class Generateur {
    private Random random;
    public Generateur(long seed){
        this.random = new Random(seed);
    }
    public TypeElement typeAleatoire() {
        TypeElement[] types = TypeElement.values();
        int index = random.nextInt(types.length);
        return types[index];
    }
}

