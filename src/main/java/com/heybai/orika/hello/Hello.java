package com.heybai.orika.hello;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class Hello {

    public static void main(String[] args) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(PersonSrc.class, PersonDst.class)
                .field("firstName", "givenName")
                .field("lastName", "sirName")
                .byDefault()
                .register();


        MapperFacade mapperFacade = mapperFactory.getMapperFacade();

        // Source
        PersonSrc personSrc = new PersonSrc("Ivan", "Grishchenko", 23, true);
        System.out.println("source: " + personSrc);

        // Simple mapping
        PersonDst personDst = mapperFacade.map(personSrc, PersonDst.class);
        System.out.println("dest: " + personDst);

        // In-place mapping
        PersonDst personDst2 = new PersonDst("aaa", "bbb", 777, "M");
        mapperFacade.map(personSrc, personDst2);
        System.out.println("dest in-place: " + personDst2);

        // BoundMapperFacade & reverse mapping
        PersonSrc personSrc2 = mapperFactory.getMapperFacade(PersonSrc.class, PersonDst.class)
                .mapReverse(personDst);
        System.out.println("back to source: " + personSrc2);
    }

    public static class PersonSrc {
        public String firstName;
        public String lastName;
        public Integer age;
        public Boolean male;

        public PersonSrc() {
        }

        public PersonSrc(String firstName, String lastName, Integer age, Boolean male) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.male = male;
        }

        @Override
        public String toString() {
            return "PersonSrc{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", age=" + age +
                    ", male=" + male +
                    '}';
        }
    }

    public static class PersonDst {
        public String givenName;
        public String sirName;
        public Integer age;
        public String gender;

        public PersonDst() {
        }

        public PersonDst(String givenName, String sirName, Integer age, String gender) {
            this.givenName = givenName;
            this.sirName = sirName;
            this.age = age;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "PersonDst{" +
                    "givenName='" + givenName + '\'' +
                    ", sirName='" + sirName + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }

}
