package com.manager.KeyManager.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class dateTimeConvertService {
  public LocalDate convertDate(String date){
    var split = date.split("-");
    LocalDate convertDate = LocalDate.of(
            Integer.parseInt(split[0]),
            Integer.parseInt(split[1]),
            Integer.parseInt(split[2])
    );
    return convertDate;
  }

  public LocalTime converTime(String time){
    var split = time.split(":");
    LocalTime convertTime = LocalTime.of(
            Integer.parseInt(split[0]),
            Integer.parseInt(split[1])
    );
    return convertTime;
  }

}
