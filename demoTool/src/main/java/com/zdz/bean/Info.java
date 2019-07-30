package com.zdz.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Setter;
import lombok.Getter;
@Entity
@Data
@Table(name = "info")
public class Info {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;


}
