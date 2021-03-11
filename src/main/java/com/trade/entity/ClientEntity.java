package com.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import com.trade.entity.composite_key.ClientId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "client")
public class ClientEntity {

    @EmbeddedId
    private ClientId clientId = new ClientId();

    @Column(length = 77)
    @NotNull
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @MapsId("typeId")
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private ClientType clientType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "clients")
    @JsonIgnoreProperties("clients")
    private Set<DealEntity> deals = new HashSet<>();

    public ClientEntity() {
    }

    public ClientEntity(ClientId clientId, String name, ClientType clientType) {
        this.clientId = clientId;
        this.name = name;
        this.clientType = clientType;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public void setClientId(ClientId clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return Objects.equals(clientId, that.clientId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(clientType, that.clientType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, name, clientType);
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", clientType=" + clientType +
                '}';
    }
}
