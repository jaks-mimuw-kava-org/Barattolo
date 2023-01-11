package utils;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class ComplexTestEntity {
    @Id
    private String name;
    @Column(name = "last")
    private String lastName;
    private Long height;
    private int age;
    private Date lastLogin;
    @Column(name = "simple_test_entity")
    @ManyToOne
    private SimpleTestEntity simpleTestEntity;

    public ComplexTestEntity(String name, String lastName, Long height, int age, Date lastLogin, SimpleTestEntity simpleTestEntity) {
        this.name = name;
        this.lastName = lastName;
        this.height = height;
        this.age = age;
        this.lastLogin = lastLogin;
        this.simpleTestEntity = simpleTestEntity;
    }

    public ComplexTestEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexTestEntity that = (ComplexTestEntity) o;
        return getAge() == that.getAge() && getName().equals(that.getName()) && getLastName().equals(that.getLastName())
                && getHeight().equals(that.getHeight()) && getLastLogin().equals(that.getLastLogin());
    }

    public SimpleTestEntity getSimpleTestEntity() {
        return simpleTestEntity;
    }

    public void setSimpleTestEntity(SimpleTestEntity simpleTestEntity) {
        this.simpleTestEntity = simpleTestEntity;
    }
}
