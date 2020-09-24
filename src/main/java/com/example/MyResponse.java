package com.example;

import java.util.List;

public class MyResponse {

  public String name;
  public List<MyData> list; // ここに想定外の値をマッピング試行した際の例外を調べる

  public static class MyData {
    public String foo;
    public String bar;
  }
}