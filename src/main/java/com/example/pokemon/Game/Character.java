package com.example.pokemon.Game;
        import javafx.scene.image.Image;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;

        public class Character {
            private int id;
            private String name;
            private int hp;
            private int attack;
            private int defense;
            private int speed;
            private int specialAttack;
            private int specialDefense;
            private String charImg;
            private ArrayList<String> type;
            private ArrayList<Attaque> AttackList;
            private Objet objet;
            //Attaque c'est une class et AttackList c'est une liste d'attaques et attaque c'est la valure de l'attaque

            public Character(int id,String name, int hp, int attack, int defense, int speed, int specialAttack, int specialDefense, String charImg, ArrayList<String> type) {
                this.name = name;
                this.id = id;
                this.hp = hp;
                this.attack = attack; // Initialize the attack attribute
                this.defense = defense;
                this.speed = speed;
                this.specialAttack = specialAttack;
                this.specialDefense = specialDefense;
                this.charImg = charImg;
                this.type = type;
                this.AttackList = new ArrayList<Attaque>();
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }

            public String getCharImg() {
                return "" + charImg;
            }

            public int getHP() {
                return hp;
            }

            public int setHP(int hp) {
                return this.hp = hp;
            }

            public ArrayList<String> getTypes() {
                return this.type;
            }

            public int getAttackValue() {
                return attack;
            }

            public int getDefense() {
                return defense;
            }

            public int getSpeed() {
                return speed;
            }

            public int getSpecialAttack() {
                return specialAttack;
            }

            public int getSpecialDefense() {
                return specialDefense;
            }

            public ArrayList<String> getType() {
                return type;
            }

            @Override
            public String toString() {
                return name;
            }

            public void specialHit(Character target) {
                double typeMultiplier = this.getType().stream()
                        .flatMap(attackType -> target.getType().stream()
                                .map(targetType -> TypeEffectiveness.getEffectiveness(attackType, targetType)))
                        .reduce(1.0, (a, b) -> a * b);

                double randomFactor = 0.85 + new Random().nextDouble() * 0.15;
                double attackPower = getSpecialAttack() * (this.getSpecialAttack() / (double) target.getSpecialDefense()) * typeMultiplier * randomFactor;
                int newhp = (target.getHP() - (int) Math.round(attackPower));
                target.setHP(newhp);

                System.out.println("Special Attack Power: " + attackPower);

            }


            public void addAttackToAttackList(Attaque attaque) {
               this.AttackList.add(attaque);
            }

            public ArrayList<Attaque> getAttackList() {
                return this.AttackList;
            }

            public void setId(int id) {
                this.id = id;
            }

            public  String getAttackListToString(){
                String result = "";
                for (Attaque attaque : this.AttackList) {
                    result += attaque.getName() + attaque.getDamage();
                }
                return result;
            }



            public void PhysicalHit(Character target, int impact) {
                double typeMultiplier = this.getType().stream()
                        .flatMap(attackType -> target.getType().stream()
                                .map(targetType -> TypeEffectiveness.getEffectiveness(attackType, targetType)))
                        .reduce(1.0, (a, b) -> a * b);

                double randomFactor = 0.85 + new Random().nextDouble() * 0.15;
                double attackPower = impact * (this.getAttackValue() / (double) target.getDefense()) * typeMultiplier * randomFactor;
                int newhp = (target.getHP() - (int) Math.round(attackPower));
                target.setHP(newhp);

                System.out.println("Special Attack Power: " + attackPower);

            }


            public void setObjet(Objet objet) {
                this.objet = objet;
            }

            public Objet getObjet() {
                return objet;
            }






        }
