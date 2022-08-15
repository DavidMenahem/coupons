package com.david.coupons.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMap {
    private long id;
    private java.lang.String email;
    private String userType;
}
