package com.example.case_module4.model;


public interface IRoomRating {

    Long getId();

    Long getCategoryId();

    Long getHostUserId();

    Double getArea();

    Double getPrice();

    Integer getBeds();

    Integer getBaths();

    Long getCityId();

    String getAddress();

    Boolean getIsAvailable();

    Long getAvgRating();

}
