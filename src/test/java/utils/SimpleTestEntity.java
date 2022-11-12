package utils;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SimpleTestEntity {
    @Id
    private Long id;
    private String name;

    public SimpleTestEntity() {
    }

    public SimpleTestEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SimpleTestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTestEntity that = (SimpleTestEntity) o;
        return getId().equals(that.getId()) && getName().equals(that.getName());
    }
}
