# HttpResponseをjsonに変換する

SpringBootを使用する。

## 実行

### ビルド＋起動

```
mvn clean
mvn compile
mvn spring-boot:run
```

### 実行結果

下記のコマンドをSpringBootのバージョンを変えて実行してみる。
```
curl http://localhost:8080/ok --head
```

SpringBoot 1.5.0.RELEASE
```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Content-Length: 56
Date: Thu, 24 Sep 2020 02:37:57 GMT
```

SpringBoot 2.3.4.RELEASE
```
HTTP/1.1 200 
Content-Type: application/json
Content-Length: 56
Date: Thu, 24 Sep 2020 02:43:16 GMT
```

Springのバージョンが上がるとContentTypeから文字コードが消えるっぽい…？(Jacksonの影響の可能性もあるが、未確認。)

## 参考

[Qiita:RestTemplate でレスポンスの JSON とクラスのマッピングに失敗した場合に発生する例外](https://qiita.com/niwasawa/items/e268dee0d1498a185890)  
アプリはここからコピペ。