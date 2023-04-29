package ru.job4j.dish.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "dishes")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        return id == dish.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
