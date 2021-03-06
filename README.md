[![Build Status](https://img.shields.io/circleci/project/wro4j/wro4j-runtime-taglib/master.svg)](https://circleci.com/gh/wro4j/wro4j-runtime-taglib/tree/master)
[![Build Status](https://img.shields.io/codecov/c/github/wro4j/wro4j-runtime-taglib/master.svg)](https://codecov.io/github/wro4j/wro4j-runtime-taglib?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lifus/wro4j-runtime-taglib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lifus/wro4j-runtime-taglib/badge.svg)

# Features

1. Run-time support for [namingStrategy][1] configuration option.
2. Configurable rendering of resource URIs. Provides _resourceUriStrategy_ configuration option.
3. Allows for rendering of external and protected unprocessed resources.
4. Legacy code friendly. Tag library allows direct access to optimized resources.
5. Wrapper-based implementation. Tag library shouldn't affect your wro4j customizations.
6. Lazy-loading. Defer initialization of data until it's first required.
7. Separate tag library context for web applications. Tag library stores it's data in servlet context scope.
8. Exhausted caching of tag library data. Creates cache using [cachingStrategy][1].
9. Adaptable to resource changes. Supports [resourceWatcherUpdatePeriod][1].

# Usage

## Prerequisites

* Java 7 or higher.

* [Java Servlet 3.0 Container][2], or newer.

* [Wro4j][3] 1.7.x.

* [WroServletContextListener and WroContextFilter in web.xml][4].
```xml
  <listener>
    <listener-class>ro.isdc.wro.http.WroServletContextListener</listener-class>
  </listener>

  <filter>
    <filter-name>WroContextFilter</filter-name>
    <filter-class>ro.isdc.wro.http.WroContextFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>WroContextFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

```

## Installation

1. Add ContextListener to web.xml *after* WroServletContextListener
  ```xml
  <listener>
    <listener-class>com.github.lifus.wro4j_runtime_taglib.servlet.TaglibServletContextListener</listener-class>
  </listener>
  ```

2. include the following tag library directive in every JSP in which optimized resources are used
  ```jsp
  <%@ taglib prefix="wro" uri="https://github.com/lifus/wro4j-runtime-taglib"%>
  ```

3. Replace usages of optimized resources with tags
  ```html
  <wro:script group="all" />
  <wro:stylesheet group="all" />
  ```

## Configuration

Use [wro.properties][3].

### resourceUriStrategy

_Default: versioned_. 

Resources that should be rendered. Specify one of the following values:

| Value       | Description                                                                              |
| ------------| ---------------------------------------------------------------------------------------- |
| versioned   | Render optimized resources and version their URIs                                        |
| unoptimized | Render unprocessed resources. Supported [uriLocators][1] are [servletContext and uri][5].|

### optimizedResourcesRootStrategy

_Default: inferred_.

Strategy to find the root of optimized resources. Specify one of the following values:

| Value      |                                                                       |
| -----------| ----------------------------------------------------------------------|
| inferred   | Use value of the first url-pattern for the first WroFilter in web.xml |
| predefined | Use value of optimizedResourcesRoot property in wro.properties         |

### optimizedResourcesRoot

The root of optimized resources. Required when `optimizedResourcesRootStrategy` is `predefined`, ignored when `optimizedResourcesRootStrategy` is `inferred`.

_Example:_ `optimizedResourcesRoot=/wro/`

### resourceDomain

The domain that is used to serve resources to your users.

_Example:_ `resourceDomain=//your-cdn.com`

# License

This project is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

[1]: https://code.google.com/p/wro4j/wiki/ConfigurationOptions
[2]: https://jcp.org/aboutJava/communityprocess/final/jsr315/
[3]: http://code.google.com/p/wro4j/wiki/Installation
[4]: https://code.google.com/p/wro4j/wiki/WroServletContextListener
[5]: https://code.google.com/p/wro4j/wiki/ResourceTypes
