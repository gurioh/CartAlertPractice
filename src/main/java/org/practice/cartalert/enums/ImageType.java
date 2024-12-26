package org.practice.cartalert.enums;


public enum ImageType {
    THUMBNAIL("썸네일"),
    DETAIL("상세이미지");

    private final String description;

    ImageType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}