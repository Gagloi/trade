package com.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "deal")
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    private BigDecimal volume;

    private Timestamp dateCreate;

    @Column(length = 2)
    private String type;

    @ManyToMany
    @JoinTable(
            name = "deal_client",
            joinColumns = @JoinColumn(name = "deal_id"),
            inverseJoinColumns = {@JoinColumn(name = "client_id"), @JoinColumn(name = "client_type")}
    )
    @JsonIgnoreProperties("deals")
    private Set<ClientEntity> clients = new HashSet<>();

    @OneToOne(mappedBy = "deal", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnoreProperties(value = "deal")
    private InvoiceEntity invoice;

    public DealEntity() {
    }

    public DealEntity(Long id, BigDecimal volume, Timestamp dateCreate, String type, Set<ClientEntity> clients, InvoiceEntity invoice) {
        this.id = id;
        this.volume = volume;
        this.dateCreate = dateCreate;
        this.type = type;
        this.clients = clients;
        this.invoice = invoice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(Set<ClientEntity> clients) {
        this.clients = clients;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealEntity that = (DealEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(dateCreate, that.dateCreate) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volume, dateCreate, type);
    }


    @Override
    public String toString() {
        return "DealEntity{" +
                "id=" + id +
                ", volume=" + volume +
                ", dateCreate=" + dateCreate +
                ", type='" + type + '\'' +
                ", invoice=" + invoice +
                '}';
    }
}
