package main.java.creators.builder;

public class Person {
    public String name;
    public String birthplace;
    public int age;
    public String school;
    public String workplace;

    private Person() {

    }

    public static class AdultBuilder {
        private Person person;

        public AdultBuilder(Person person) {
            this.person = person;
        }

        public AdultBuilder setAdult(int age) {
            if(age < 18) throw new IllegalArgumentException("The age of majority is 18");
            person.age = age;
            return this;
        }

        public AdultBuilder setWorkplace(String workplace) {
            this.person.workplace = workplace;
            return this;
        }

        public Person build() {
            return this.person;
        }

    }

    public static class UnderageBuilder {
        private Person person;
    
        public UnderageBuilder(Person person) {
            this.person = person;
        }
    
        public UnderageBuilder setBirthplace(String birthplace) {
            person.birthplace = birthplace;
            return this;
        }

        public UnderageBuilder setSchool(String school) {
            this.person.school = school;
            return this;
        }
    
        public UnderageBuilder setUnderAge(int age, String school) {
            if(age >= 18) throw new IllegalArgumentException("The age of majority is 18");
            person.age = age;
            person.school = school;
            person.workplace = null;
            return this;
        }
    
        public Person build() {
            return person;
        }

    }
}
