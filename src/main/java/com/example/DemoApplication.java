package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  // OK: JSON を返却
  @GetMapping("/ok")
  public Map ok() {
    return
      Map.of(
        "name", "myname",
        "list", List.of(
          Map.of("foo", "myfoo", "bar", "mybar")
        )
      );
  }

  // NG1: MyResponse に合わない JSON を返却
  @GetMapping("/ng1")
  public Map ng1() {
    return
      Map.of(
        "name", "myname",
        "list", "mylist" // List があるべき場所に文字列 "mylist"
      );
  }

  // NG2: MyResponse に合わない JSON を返却
  @GetMapping("/ng2")
  public Map ng2() {
    return
      Map.of(
        "name", "myname",
        "list", "" // List があるべき場所に空文字列
      );
  }

  // NG3: MyResponse に合わない JSON を返却
  @GetMapping("/ng3")
  public Map ng3() {
    return
      Map.of(
        "name", "myname",
        "list", List.of("mylist") // MyData があるべき場所に文字列 "mylist"
      );
  }

  // NG4: MyResponse に合わない JSON を返却
  @GetMapping("/ng4")
  public Map ng4() {
    return
      Map.of(
        "name", "myname",
        "list", List.of("") // MyData があるべき場所に空文字列
      );
  }

  @GetMapping("/mydata/{path}")
  public MyResponse mydata(@PathVariable("path") String path) throws Exception {
    try {
      // 自らのサーバから JSON を取得
      String uri = "http://localhost:8080/" + path;
      RequestEntity re = RequestEntity.get(new URI(uri)).build();
      RestTemplate restTemplate = new RestTemplate();

      // JSON を MyResponse に変換
      // JSON が MyResponse に合わなければここで例外が発生する
      ResponseEntity<MyResponse> res = restTemplate.exchange(re, MyResponse.class);

      return res.getBody();

    } catch (Exception e) {

      // どういう例外が発生しているか出力する
      System.out.println("例外クラス: " + e.getClass().getName());

      // 原因となった例外のチェーンを出力する
      Throwable cause = e;
      while ((cause = cause.getCause()) != null) {
        System.out.println("原因例外クラス: " + cause.getClass().getName());
      }

      throw e;
    }
  }
}