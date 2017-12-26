1、com.dazong.common.web.monitor.SimpleMonitorServlet该类为检测接口类，用于判断业务服务器能否正常处理HTTP请求。更新完最新的dz-common-web包后，如果应用服务器支持Servlet3.0，则自动加载；如果应用服务器支持低于Servlet3.0，则需要在项目web.xml中增加配置：

```xml
<servlet>
	<servlet-name>SimpleMonitor</servlet-name>
	<servlet-class>com.dazong.common.web.monitor.SimpleMonitorServlet</servlet-class>
</servlet>
<servlet-mapping>
	<servlet-name>SimpleMonitor</servlet-name>
	<url-pattern>/simpleMonitor</url-pattern>
</servlet-mapping>
```

请测试该接口是否能正常处理请求。如http://taohua.dazong.com/simpleMonitor