# JUnit5リファレンス

## 環境
- Eclipse
- Java17
- Junit5
- Maven

## 設定手順

### 1.Eclipse に JDK 17 を認識させる

```
Window > Preferences
 → Java > Installed JREs
 → Add > Standard VM
 → JDK 17 のパスを指定
```

### 2.Maven プロジェクト作成


- File > New > Maven Project
- maven-archetype-quickstart

### 3.Eclipse の Project 設定確認
- プロジェクト右クリック > Properties

```
Java Compiler
	- Enable project specific settings
	- Compiler compliance level：17

Java Build Path
	- JRE System Library：JavaSE-17
```

### 4.pom.xml 設定

- 下記を張り付ける
```xml
  <dependencies>
    <!-- JUnit 5 -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>${junit.jupiter.version}</version>
      <scope>test</scope>
    </dependency>

    <!-- Mockito core -->
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-core</artifactId>
      <version>${mockito.version}</version>
      <scope>test</scope>
    </dependency>

    <!-- Mockito inline（static / final / private 対応） -->
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-inline</artifactId>
      <version>${mockito.version}</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
```