package com.trade.entity.composite_key;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ClientId implements Serializable {

    @Column(name = "id")
    private Long id;

    @Column(name = "type_id")
    private Long typeId;

}
