package com.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-9-7.
 */
public class Employee {

    private String name;
    private List<Employee> children;
a
    public static void main(String[] args) {
        Employee one = new Employee();
        one.setName("张三");

        Employee two = new Employee();
        two.setName("李四");
        Employee tree = new Employee();
        tree.setName("王五");
        two.getChildren().add(tree);

        one.getChildren().add(two);

        one.printSelfAndChildren();

    }

    public void printSelfAndChildren() {
        printEmployee(null, this);
    }

    private void printEmployee(String leader, Employee employee) {
        StringBuilder builder = new StringBuilder();
        if (leader != null) {
            builder.append(leader);
            builder.append("->");
        }
        builder.append(employee.getName());
        System.out.println(builder.toString());
        if (employee.getChildren() != null) {
            for (Employee children : employee.getChildren()) {
                printEmployee(builder.toString(), children);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<Employee> children) {
        this.children = children;
    }
}
