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

* [Java Servlet 3.0 API][2] or higher.

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

### Step 1: Add ContextListener to web.xml *after* WroServletContextListener
```xml
  <listener>
    <listener-class>by.wro.runtime.taglib.servlet.TaglibServletContextListener</listener-class>
  </listener>
```

### Step 2: include the following tag library directive in every JSP in which optimized resources are used
```jsp
<%@ taglib prefix="wro" uri="https://github.com/lifus/wro4j-runtime-taglib"%>
```

### Step 3: Replace usages of optimized resources with tags
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

# License

This project is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

[1]: https://code.google.com/p/wro4j/wiki/ConfigurationOptions
[2]: https://jcp.org/aboutJava/communityprocess/final/jsr315/
[3]: http://code.google.com/p/wro4j/wiki/Installation
[4]: https://code.google.com/p/wro4j/wiki/WroServletContextListener
[5]: https://code.google.com/p/wro4j/wiki/ResourceTypes
