package com.fastcampus.loan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {

  SUCCESS("0000", "success"),

  // file = 4000
  NOT_EXIST("4001", "file not exist"),

  SYSTEM_ERROR("9000", "system error");

  private final String code;
  private final String desc;
}
