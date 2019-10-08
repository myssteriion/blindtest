package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.tools.Tool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * The AvatarDTO.
 */
@Entity
@Table(name = "avatar", uniqueConstraints={ @UniqueConstraint(name = "name_unique", columnNames={"name"}) })
public class AvatarDTO extends AbstractDTO {

    /**
     * The DB id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avatar_sequence")
    @SequenceGenerator(name = "avatar_sequence", sequenceName = "avatar_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    protected Integer id;

    /**
     * The name.
     */
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    /**
     * If the file exists.
     */
    @Transient
    private Flux flux;



    /**
     * Instantiates a new Avatar dto.
     */
    public AvatarDTO() {
        this("");
    }

    /**
     * Instantiates a new AvatarDTO.
     *
     * @param name the name
     */
    public AvatarDTO(String name) {
        this.name = Tool.isNullOrEmpty(name) ? "" : name;
    }



    /**
     * Gets id.
     *
     * @return The id.
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id The id.
     */
    @Override
    public AvatarDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Gets name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     *
     * @param name The name.
     * @return the name
     */
    public AvatarDTO setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets flux.
     *
     * @return The flux.
     */
    public Flux getFlux() {
        return flux;
    }

    /**
     * Set flux.
     *
     * @param flux The flux.
     * @return this
     */
    public AvatarDTO setFlux(Flux flux) {
        this.flux = flux;
        return this;
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        AvatarDTO other = (AvatarDTO) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "id="+ id +
                ", name=" + name +
                ", flux={" + flux + "}";

    }

}
