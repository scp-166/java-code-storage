package demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lyl
 * @date 2020/10/12
 */
public class Person {
    public int id;
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person() {
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>(20);
        IntStream.range(0, 10).forEach(i->{
            personList.add(new Person(i, UUID.randomUUID().toString().replace("-", "")));
        });
        IntStream.range(0, 10).forEach(i->{
            personList.add(new Person(i, UUID.randomUUID().toString().replace("-", "")));
        });

        Map<Integer, List<Person>> ret = personList.stream().collect(Collectors.groupingBy(Person::getId));
        System.out.println(ret);

    }
}
