package ua.com.gavluk.turing.ecommerce.core;

import java.util.Objects;


public abstract class DbEntity {

    public abstract Long getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbEntity dbEntity = (DbEntity) o;
        return Objects.equals(this.getId(), dbEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + this.getId() +
                "}";
    }
}
