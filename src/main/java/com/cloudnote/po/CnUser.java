package com.cloudnote.po;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CnUser {

  private int userId;
  private String uname;
  private String upwd;
  private String nick;
  private String head;
  private String mood;

  public CnUser(String userName, String userPwd) {
    this.uname = userName;
    this.upwd = userPwd;
  }
}
