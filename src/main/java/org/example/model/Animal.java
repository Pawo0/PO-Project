package org.example.model;


import org.example.simulations.SimulationSettings;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement {

    private Vector2d position;
    private int energy;
    private int childrenCounter;
    private int age;
    private List<Animal> children;
    private int deathDate;
    private MapDirection mapDirection;
    private Genome genotype;
    private int geneIndex;
    private int grassEaten;
    private SimulationSettings settings;


    public Animal(Genome genotype,Vector2d position, SimulationSettings settings) {

        this.position = position;
        this.energy = settings.getStartAnimalEnergy();
        this.childrenCounter = 0;
        this.age = 0;
        this.children = new ArrayList<>();
        this.settings = settings;

        this.mapDirection = MapDirection.NORTH;
        this.genotype = genotype;
        this.geneIndex = 0;
        this.grassEaten = 0;
        this.deathDate = -1;
    }


    public void move() {
        MapDirection newDirection = this.mapDirection.rotate(genotype.getGen(geneIndex%genotype.getGenomeSize()));
        this.mapDirection = this.mapDirection.rotate(genotype.getGen(geneIndex%genotype.getGenomeSize()));
        Vector2d newPosition = this.position.add(newDirection.toUnitVector());
        if (newPosition.getY() > settings.getMapHeight()-1 || newPosition.getY() < 0){
            bounceBack();
        }else if (newPosition.getX() > settings.getMapWidth()-1 || newPosition.getX() < 0){
            this.position = new Vector2d((settings.getMapWidth() + newPosition.getX())%settings.getMapWidth(), newPosition.getY());
        }
        else {
            this.position = newPosition;
        }

        this.geneIndex++;
        this.energy--;
    }

    public void birthChild(Animal child, int energyToSubtract) {
        this.children.add(child);
        this.childrenCounter++;
        this.energy -= energyToSubtract;
    }

    public void eat(){
        this.energy += settings.getEnergyGainedFromEating();
        this.grassEaten++;
    }

    public void incrementAge(){
        this.age++;
    }

    public void animalDeath(){
        this.deathDate=this.age;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public int getChildrenCounter() {
        return childrenCounter;
    }

    public int getAge() {
        return age;
    }

    public List<Animal> getChildren() {
        return children;
    }

    public MapDirection getMapDirection() {
        return mapDirection;
    }

    public Genome getGenotype() {
        return genotype;
    }

    public int getGeneIndex() {
        return geneIndex;
    }

    public int getGrassEaten() {
        return grassEaten;
    }

    public int getDeathDate() {
        return deathDate;
    }

    public void subtractCopulationEnergy(int energyToSubtract) {
        this.energy -= energyToSubtract;
    }

    private void bounceBack(){
        this.mapDirection = this.mapDirection.rotate(4);
    }

    @Override
    public String toString(){
        return this.energy >= 0 ? String.valueOf(this.energy) : "X";
    }
}
