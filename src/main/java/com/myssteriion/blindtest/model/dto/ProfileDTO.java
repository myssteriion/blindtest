package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.model.dto.AbstractDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * The ProfileDTO.
 */
@Entity
@Table(name = "profile", uniqueConstraints={ @UniqueConstraint(name = "profile__name__unique", columnNames={"name"}) })
@SequenceGenerator(name = "sequence_id", sequenceName = "profile_sequence", allocationSize = 1)
public class ProfileDTO extends AbstractDTO {
    
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
    @PositiveOrZero(message = "Background can't be negative.")
    private Integer background;
    
    /**
     * The avatar name.
     */
    @Column(name = "avatar_name", nullable = false)
    @NotEmpty(message = "AvatarName can't be empty.")
    private String avatarName;
    
    /**
     * The avatar.
     */
    @Transient
    private AvatarDTO avatar;
    
    
    
    /**
     * Instantiates a new Profile dto.
     */
    public ProfileDTO() {
        this("");
    }
    
    /**
     * Instantiates a new Profile dto.
     *
     * @param name the name
     */
    public ProfileDTO(String name) {
        this(name, "");
    }
    
    /**
     * Instantiates a new Profile dto.
     *
     * @param name       the name
     * @param avatarName the avatar name
     */
    public ProfileDTO(String name, String avatarName) {
        this(name, avatarName, 0);
    }
    
    /**
     * Instantiates a new Profile dto.
     *
     * @param name       the name
     * @param avatarName the avatar name
     * @param background the background
     */
    public ProfileDTO(String name, String avatarName, Integer background) {
        
        this.name = CommonUtils.isNullOrEmpty(name) ? "" : name.trim();
        this.background = Math.max(background, 0);
        this.avatarName = CommonUtils.isNullOrEmpty(avatarName) ? Constant.DEFAULT_AVATAR : avatarName;
        this.avatar = new AvatarDTO(this.avatarName);
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
    public ProfileDTO setBackground(Integer background) {
        this.background = background;
        return this;
    }
    
    /**
     * Set avatar.
     *
     * @param avatar the avatar.
     * @return this
     */
    public ProfileDTO setAvatar(AvatarDTO avatar) {
        this.avatar = avatar;
        return this;
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
    public ProfileDTO setName(String name) {
        this.name = CommonUtils.isNullOrEmpty(name) ? "" : name.trim();
        return this;
    }
    
    /**
     * Gets avatar name.
     *
     * @return the avatar name
     */
    public String getAvatarName() {
        return avatarName;
    }
    
    /**
     * Sets avatar name.
     *
     * @param avatarName the avatar name
     * @return this
     */
    public ProfileDTO setAvatarName(String avatarName) {
        this.avatarName = CommonUtils.isNullOrEmpty(avatarName) ? Constant.DEFAULT_AVATAR : avatarName;
        this.avatar = new AvatarDTO(this.avatarName);
        return this;
    }
    
    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public AvatarDTO getAvatar() {
        return avatar;
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
        
        ProfileDTO other = (ProfileDTO) obj;
        return Objects.equals(this.name, other.name);
    }
    
    @Override
    public String toString() {
        return super.toString() +
                ", background=" + background +
                ", name=" + name +
                ", avatarName=" + avatarName +
                ", avatar={" + avatar + "}";
    }
    
}
