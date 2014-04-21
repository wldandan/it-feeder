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

    @Indexed(unique = true)
    @Property("image_id")
    private String imageId;

    @Indexed
    private String title;

    @Indexed
    @Property("device_id")
    private String deviceId;

    @Indexed
    @Property("land_id")
    private String landId;

    @Transient
    private String imageExt;

    @Property("image_type")
    private ImageType imageType = ImageType.NONE;

    @Property("violation_type")
    private ViolationType violationType = ViolationType.NONE;

    @Indexed
    @Property("captured_at")
    private Date capturedAt;

    private String description;

    @Embedded
    private Source source;

    @Embedded
    private Vehicle vehicle;

    @Embedded
    private Geo geo;

    @Property("image_handle_status")
    private Integer imageHandleStatus = 0;

    @Property("distributed_path")
    private String distributedPath;

    @Indexed
    private boolean deleted;

    @Indexed
    private boolean expired;

    @Indexed
    private boolean qualified;

    @Version
    private Long lock;

    @Property("created_at")
    private Date createdAt;

    @Property("updated_at")
    private Date updatedAt;

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

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Integer getImageHandleStatus() {
        return imageHandleStatus;
    }

    public void setImageHandleStatus(Integer imageHandleStatus) {
        this.imageHandleStatus = imageHandleStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLandId() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
    }

    public String getImageExt() {
        return imageExt;
    }

    public void setImageExt(String imageExt) {
        this.imageExt = imageExt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isQualified() {
        return qualified;
    }

    public void setQualified(boolean qualified) {
        this.qualified = qualified;
    }

    public String getPath(){
        return getSource().getPath();
    }

}
