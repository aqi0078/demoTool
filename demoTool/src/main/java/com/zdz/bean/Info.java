package com.zdz.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "info")
public  class Info {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_time")
    private Timestamp dateTime;


}
