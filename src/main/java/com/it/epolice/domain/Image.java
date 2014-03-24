package com.it.epolice.domain;

import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.io.Serializable;
import java.util.Date;

@Entity(value = "cases", noClassnameStored = true)
public class Image implements Serializable {

    @Id
    private ObjectId id;

    @Property("image_id")
    private String imageId;

    @Indexed(unique = true)
    private String name;

    @Transient
    private String group;

    @Indexed(unique = true)
    @Property("captured_at")
    private Date capturedAt;

    @Property("original_path")
    private String originalPath;


    @Property("distributed_path")
    private String distributedPath;


    @Embedded
    private Vehicle vehicle;

    @Property("image_type")
    private ImageType imageType = ImageType.NONE;

    @Property("violation_type")
    private ViolationType violationType = ViolationType.NONE;

    private String description;


    @Transient
    private String content;

    @Version
    private Long lock;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
