package guru.springframework.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Test {

    @Size(min = 3, max = 10, message = "Name can't be less than 3 and greater than 10")
    private String name;

    @NotNull
    @Min(value = 30)
    private Integer age;

    public Test() {
    }

    public Test(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
