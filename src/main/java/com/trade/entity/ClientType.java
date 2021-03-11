package com.trade.entity;

import com.sun.istack.NotNull;
import com.trade.entity.enums.ClientTypeName;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ClientType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 11)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ClientTypeName name;

    public ClientType(Long id, ClientTypeName name) {
        this.id = id;
        this.name = name;
    }

    public ClientType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientTypeName getName() {
        return name;
    }

    public void setName(ClientTypeName name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientType that = (ClientType) o;
        return Objects.equals(id, that.id) &&
                name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ClientType{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
