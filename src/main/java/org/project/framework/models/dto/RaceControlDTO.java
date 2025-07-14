package org.project.framework.models.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RaceControlDTO {
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    protected OffsetDateTime date;

    @JsonProperty("category")
    protected String category;

    @JsonIgnore
    protected String subTitle;

    @JsonProperty("flag")
    protected String flag;

    @JsonProperty("message")
    protected String message;

    public RaceControlDTO() {}

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("subTitle")
    public String getSubTitle() {
        return resolveSubtitle(flag);
    }

    public abstract String resolveSubtitle(String flag);
}
