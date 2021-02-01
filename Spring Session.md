# Spring Session

Spring Session provides an API and implementations for managing a user’s session information.

Spring Session提供了管理用户会话信息的API和实现。

## Features

Spring Session makes it trivial to support clustered sessions without being tied to an application container specific solution. It also provides transparent integration with:

Spring Session使支持集群会话变得非常简单，而不需要绑定到特定于应用程序容器的解决方案。它还提供了与以下组件的透明集成:

- `HttpSession` - allows replacing the HttpSession in an application container (i.e. Tomcat) neutral way, with support for providing session IDs in headers to work with RESTful APIs

  HttpSession ——允许以中立的方式替换应用程序容器(即Tomcat)中的HttpSession，并支持在报头中提供会话id来与RESTful api一起工作

- `WebSocket` - provides the ability to keep the HttpSession alive when receiving WebSocket messages

   WebSocket  -提供了在接收WebSocket消息时保持HttpSession活着的能力

- `WebSession` - allows replacing the Spring WebFlux’s WebSession in an application container neutral way

  WebSession-允许以与应用程序容器无关的方式替换Spring WebFlux的WebSession

## Modules

Spring Session consists of the following modules:

Spring Session由以下模块组成:

- Spring Session Core - provides core Spring Session functionalities and APIs

  Spring Session Core——提供核心的Spring Session功能和api

- Spring Session Data Redis - provides SessionRepository and ReactiveSessionRepository implementation backed by Redis and configuration support

  提供了Redis支持的SessionRepository和ReactiveSessionRepository实现和配置支持

- Spring Session JDBC - provides SessionRepository implementation backed by a relational database and configuration support

  Spring Session JDBC -提供了由关系数据库和配置支持的SessionRepository实现

- Spring Session Hazelcast - provides SessionRepository implementation backed by Hazelcast and configuration support

  Spring Session Hazelcast -提供了支持Hazelcast和配置支持的SessionRepository实现

## Adding Spring Session to your build

This project uses a Maven BOM (Bill of Materials) and a **release train** to coordinate versions, e.g. `Bean-SR10`, `Corn-SR2`, etc.

这个项目使用一个Maven BOM(物料清单)和一个**发布序列**来协调版本，例如:“Bean-SR10”,“Corn-SR2”等。

### Using the BOM with Maven

With Maven, you need to import the BOM first:

```
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.session</groupId>
      <artifactId>spring-session-bom</artifactId>
      <version>Corn-SR2</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

- This example is using `Corn-SR2`, but you plug in the release train version you need.

  这个例子使用的是' Corn-SR2 '，但是你插入了你需要的版本。

- Notice the use of the `<dependencyManagement>` section and the `import` scope.

  请注意' <dependencyManagement> '节和' import '作用域的使用。

Next, add your dependencies to the project without a `<version>`:

接下来，在没有' <version> '的情况下将依赖项添加到项目中:

```
<dependencies>
  <dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
  </dependency>
</dependencies>
```

### Using the BOM with Gradle

Since Gradle has no first-class support for Maven BOMs, you can use Spring’s [Dependency management plugin](https://plugins.gradle.org/plugin/io.spring.dependency-management).

Apply the plugin from Gradle Plugin Portal (update the version if needed):

```
plugins {
  id 'io.spring.dependency-management' version '1.0.9.RELEASE'
}COPY
```

Then use it to import the BOM:

```
dependencyManagement {
  imports {
    mavenBom 'org.springframework.session:spring-session-bom:Corn-SR2'
  }
}COPY
```

Finally, add a dependency to the project without a version:

```
dependencies {
  compile 'org.springframework.session:spring-session-data-redis'
}
```

# Spring Session - HttpSession

本指南描述了如何使用Spring Session透明地利用Redis来支持web应用程序的HttpSession与Java配置。

## 1. Updating Dependencies

在使用Spring Session之前，必须更新依赖项。如果您正在使用Maven，您必须添加以下依赖项:

pom.xml

```xml
<dependencies>
	<!-- ... -->

	<dependency>
		<groupId>org.springframework.session</groupId>
		<artifactId>spring-session-data-redis</artifactId>
		<version>2.3.2.RELEASE</version>
		<type>pom</type>
	</dependency>
	<dependency>
		<groupId>io.lettuce</groupId>
		<artifactId>lettuce-core</artifactId>
		<version>5.2.2.RELEASE</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-web</artifactId>
		<version>5.2.12.RELEASE</version>
	</dependency>
</dependencies>
```

## 2. Spring Java Configuration

在添加所需的依赖项之后，我们可以创建Spring配置。Spring配置负责创建servlet过滤器，该过滤器将HttpSession实现替换为由Spring Session支持的实现。为此，添加以下Spring配置:

```java
@EnableRedisHttpSession 
public class Config {

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(); 
	}

}
```

1，` @EnableRedisHttpSession `注释创建了一个名为' springSessionRepositoryFilter '的实现了' Filter '的Spring Bean。过滤器负责替换Spring Session支持的“HttpSession”实现。在这个例子中，Spring Session是由Redis支持的。

2，我们创建一个' RedisConnectionFactory '连接Spring会话到Redis服务器。我们将连接配置为连接到默认端口(6379)上的本地主机。

## 3. Java Servlet Container Initialization

我们的Spring配置创建了一个名为springSessionRepositoryFilter的Spring Bean，它实现了Filter。springSessionRepositoryFilter bean负责用Spring Session支持的自定义实现替换HttpSession。

为了让过滤器发挥它的魔力，Spring需要加载我们的Config类。最后，我们需要确保Servlet容器(即Tomcat)对每个请求都使用springSessionRepositoryFilter。幸运的是，Spring Session提供了一个名为AbstractHttpSessionApplicationInitializer的实用程序类，可以简化这两个步骤。示例如下:

src/main/java/sample/Initializer.java

```java
public class Initializer extends AbstractHttpSessionApplicationInitializer { 

	public Initializer() {
		super(Config.class); 
	}

}
```

##  4. httpsession Sample Application

### 4.1. Running the `httpsession` Sample Application

您可以通过获取源代码并调用以下命令来运行示例:

```sh
$ ./gradlew :spring-session-sample-javaconfig-redis:tomcatRun
```

### 4.2. Exploring the `httpsession` Sample Application

现在您可以尝试使用该应用程序。为此，请填写以下信息的表格:

- **Attribute Name:** *username*
- **Attribute Value:** *rob*

现在点击**Set Attribute**按钮。现在您应该看到表中显示的值。

### 4.3. How Does It Work?

We interact with the standard `HttpSession` in the `SessionServlet` shown in the following listing:

我们与“SessionServlet”中的标准“HttpSession”进行交互，如下面的清单所示:

```java
@WebServlet("/session")
public class SessionServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String attributeName = req.getParameter("attributeName");
		String attributeValue = req.getParameter("attributeValue");
		req.getSession().setAttribute(attributeName, attributeValue);
		resp.sendRedirect(req.getContextPath() + "/");
	}

	private static final long serialVersionUID = 2878267318695777395L;

}
```

而不是使用Tomcat的`HttpSession`，我们在Redis中持久化值。Spring Session在浏览器中创建一个名为`Session`的cookie。该cookie包含会话的ID。您可以使用[Chrome]或[Firefox]查看cookies。

可以通过redis-cli删除会话。例如，在基于Linux的系统上，您可以输入以下内容:

```
	$ redis-cli keys '*' | xargs redis-cli del
```

或者，您也可以删除显式键。在终端输入以下内容，确保将`7e8383a4-082c-4ffe-a4bc-c40fd3363c5e`替换为会话cookie的值:

```
	$ redis-cli del spring:session:sessions:7e8383a4-082c-4ffe-a4bc-c40fd3363c5e
```

现在您可以在http://localhost:8080/访问该应用程序，并看到我们添加的属性不再显示。