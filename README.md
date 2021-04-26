# SM9-Start-Util
sm2、sm3、sm4、sm9密码服务封装


## 使用说明



**maven 依赖**

```java
<!--引入jpbc依赖-->
<dependencies> 
<dependency>
    <groupId>it.unisa.dia.gas</groupId>
    <artifactId>jpbc-api</artifactId>
    <version>2.0.0</version>
</dependency>
<dependency>
    <groupId>it.unisa.dia.gas</groupId>
    <artifactId>jpbc-plaf</artifactId>
    <version>2.0.0</version>
</dependency>
</dependencies> 
  
  
 <dependency>
            <groupId>com.github.iupikachu</groupId>
            <artifactId>SM9-Start-Util</artifactId>
            <version>0.0.1</version>
 </dependency>
  
  
 <repositories>
        <repository>
            <id>lambdaupb.jpbc.fake</id>
            <name>UNMAINTAINED jPBC maven repository</name>
            <url>https://raw.github.com/lambdaupb/maven-jpbc/master/</url>
        </repository>
        <repository>
            <id>jitpack.io</id>
            <url>https://www.jitpack.io</url>
        </repository>
 </repositories>
```

### SM2椭圆曲线公钥密码算法

```java
// 加密
String encrypt(String plainText) throws IOException;

// 解密
String decrypt(String cipherText) throws IOException;

// 签名
SM2SignVO sign(String message);

// 验签
Boolean verify(String message ,SM2SignVO sign);
```



```java
@Autowired
SM2Service sm2Service;

@Test
public void testSM2Service() throws IOException {
  
    String s = "acising";
    String cipherText = sm2Service.encrypt(s);
    String plainText = sm2Service.decrypt(cipherText);
    System.out.println(plainText);
    SM2SignVO sign = sm2Service.sign(s);
    Boolean verify = sm2Service.verify(s, sign);
    System.out.println("验签是否成功:"+verify);
}
```





### SM3杂凑算法



```java
// 获得64位杂凑值
String getHash(String message);
```



```java
@Autowired
SM3Service sm3Service;

@Test
public void testSM3(){
    String s = sm3Service.getHash("acising");
    System.out.println(s);
}
```



### SM4对称算法

```java
// encrypt ECB加密
String encrypt_ECB(String message);

// decrypt ECB解密
String decrypt_ECB(String cipherText);

// encrypt CBC加密
String encrypt_CBC(String message);

// decrypt CBC解密
String decrypt_CBC(String cipherText);
```



```java
@Autowired
SM4Service sm4Service;

@Test
public void testSM4(){
    String message = "acising";
    String s1 = sm4Service.encrypt_ECB(message);
    System.out.println("ECB加密:"+s1);
    String s2 = sm4Service.decrypt_ECB(s1);
    System.out.println("ECB解密:"+s2);
    String s3 = sm4Service.encrypt_CBC(message);
    System.out.println("CBC加密:"+s3);
    String s4 = sm4Service.decrypt_CBC(s3);
    System.out.println("CBC解密:"+s4);
}
```





### SM9标识密码算法

```java
// 加密服务
ResultCipherText encrypt(String IBE_Identify,String msg) throws Exception;

// 解密服务
String decrypt(String IBE_Identify,ResultCipherText resultCipherText) throws Exception;

// 注册
Map<String, Object> register(String IBE_Identify) throws Exception;
```



```java
@Autowired
SM9Service sm9Service;

@Test
public void testSM9(){
String id = "Bob";
String msg ="acising";
ResultCipherText resultCipherText = sm9Service.encrypt(id,msg);
String s1 = sm9Service.decrypt(id, resultCipherText);
System.out.println("s1:"+s1);
}
```

