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

```sh
	<dependencies>
		<!-- JUnit 5 本体 -->
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter</artifactId>
			<scope>test</scope>
		</dependency>
		<!-- Mockito 5 (inline機能がデフォルトで含まれる) -->
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-core</artifactId>
			<version>5.11.0</version>
			<scope>test</scope>
		</dependency>
		<!-- JUnit 5とMockitoを連携させる拡張 -->
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-junit-jupiter</artifactId>
			<version>5.11.0</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

