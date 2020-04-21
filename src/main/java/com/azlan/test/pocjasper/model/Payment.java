package com.azlan.test.pocjasper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String refNumber;
    private Date date;
    private String fromAccount;
    private String toAccount;
}
