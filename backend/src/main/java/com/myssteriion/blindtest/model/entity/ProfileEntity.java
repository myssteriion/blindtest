package com.myssteriion.blindtest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myssteriion.utils.model.entity.AbstractEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * The ProfileEntity.
 */
@Entity
@Table(name = "profile",
        uniqueConstraints = {
                @UniqueConstraint( name = "profile__name__unique", columnNames = { "name" } ),
                @UniqueConstraint( name = "profile__profile_stat_id__unique", columnNames = { "profile_stat_id" } )
        })
@SequenceGenerator(name = "sequence_id", sequenceName = "profile_sequence", allocationSize = 1)
@JsonIgnoreProperties(value = { "profileStat" }, allowGetters = true)
public class ProfileEntity extends AbstractEntity<ProfileEntity> {
    
    /**
     * The name.
     */
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name can't be empty.")
    private String name;
    
    /**
     * The background (card color).
     */
    @Column(name = "background", nullable = false)
    @ColumnDefault("0")
    @PositiveOrZero(message = "Background can't be negative.")
    private Integer background;
    
    /**
     * The avatar.
     */
    @ManyToOne
    @JoinColumn( name = "avatar_id", foreignKey = @ForeignKey(name = "profile__avatar_id__fk") )
    private AvatarEntity avatar;
    
    /**
     * The profile.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn( name = "profile_stat_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "profile__profile_stat_id__fk") )
    @JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
    private ProfileStatEntity profileStat = new ProfileStatEntity();
    
    
    
    /**
     * Instantiates a new Profile dto.
     */
    public ProfileEntity() {
    }
    
    
    
    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets name.
     *
     * @param name the name
     * @return this
     */
    public ProfileEntity setName(String name) {
        this.name = name;
        return this;
    }
    
    /**
     * Gets background.
     *
     * @return the background.
     */
    public Integer getBackground() {
        return background;
    }
    
    /**
     * Set background.
     *
     * @param background the background.
     * @return this
     */
    public ProfileEntity setBackground(Integer background) {
        this.background = background;
        return this;
    }
    
    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public AvatarEntity getAvatar() {
        return avatar;
    }
    
    /**
     * Set avatar.
     *
     * @param avatar the avatar.
     * @return this
     */
    public ProfileEntity setAvatar(AvatarEntity avatar) {
        this.avatar = avatar;
        return this;
    }
    
    /**
     * Get profileStat.
     *
     * @return the profileStat
     */
    public ProfileStatEntity getProfileStat() {
        return profileStat;
    }
    
    /**
     * Set profileStat.
     *
     * @param profileStat the profileStat
     * @return this
     */
    public ProfileEntity setProfileStat(ProfileStatEntity profileStat) {
        this.profileStat = profileStat;
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
        
        ProfileEntity other = (ProfileEntity) obj;
        return Objects.equals(this.name, other.name);
    }
    
    @Override
    public String toString() {
        return super.toString() +
                ", name=" + name +
                ", background=" + background +
                ", avatar={" + avatar + "}" +
                ", profileStat={" + profileStat + "}";
    }
    
}
