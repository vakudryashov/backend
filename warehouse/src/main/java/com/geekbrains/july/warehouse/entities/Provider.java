package com.geekbrains.july.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;


@NoArgsConstructor
@Setter
@Getter
public class Provider {

    private int id;

    private String name;

    private String email;

    private String phone;

    private Date creationDate;


}
