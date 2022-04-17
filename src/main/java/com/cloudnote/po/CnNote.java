package com.cloudnote.po;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CnNote {

  private long noteId;
  private String title;
  private String content;
  private long typeId;
  private Timestamp pubTime;
  private double lon;
  private double lat;

}
