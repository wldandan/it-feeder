package com.it.epolice.domain;

import com.google.gson.Gson;
import com.sun.xml.internal.ws.api.PropertySet;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.io.Serializable;
import java.util.Date;

@Entity(value = "cases", noClassnameStored = true)
public class Image implements Serializable {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Property("image_id")
    private String imageId;

    @Indexed(unique = true)
    private String title;

    @Property("created_at")
    private Date createdAt;

    @Property("updated_at")
    private Date updatedAt;

    @Indexed
    @Property("captured_at")
    private Date capturedAt;

    @Property("original_path")
    private String originalPath;

    @Property("distributed_path")
    private String distributedPath;

    @Embedded
    private Vehicle vehicle;

    @Embedded
    private Geo geo;

    @Property("image_type")
    private ImageType imageType = ImageType.NONE;

    @Property("image_status")
    private ImageStatus imageStatus = ImageStatus.NONE;


    @Property("violation_type")
    private ViolationType violationType = ViolationType.NONE;

    private String description;

    private String extension;

    @Indexed
    private boolean deleted;

    @Indexed
    private boolean expired;

    @Version
    private Long lock;

    @Transient
    private String content;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setViolationType(ViolationType violationType) {
        this.violationType = violationType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public ImageType getImageType() {
        return imageType;
    }


    public String getDescription() {
        return description;
    }

    public Date getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(Date capturedAt) {
        this.capturedAt = capturedAt;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getDistributedPath() {
        return distributedPath;
    }

    public void setDistributedPath(String distributedPath) {
        this.distributedPath = distributedPath;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public Long getLock() {
        return lock;
    }

    public void setLock(Long lock) {
        this.lock = lock;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getExtension() {
        return (null == extension) ? "JPG" : extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String generateId(){
        return "";
    }
}
