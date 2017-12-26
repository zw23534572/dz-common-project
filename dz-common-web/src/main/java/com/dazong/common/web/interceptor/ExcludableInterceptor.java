package com.dazong.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 支持可排除拦截验证的Interceptor
 * @author sizs
 *
 */
public abstract class ExcludableInterceptor extends HandlerInterceptorAdapter {

    private final static Logger log = LoggerFactory.getLogger(ExcludableInterceptor.class);
    
    protected String excludePath;
    
    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
    }
    
    /*Interceptor说明：
     发起请求,进入拦截器链，运行所有拦截器的preHandle方法，
    1.当preHandle方法返回false时，从当前拦截器往回执行所有拦截器的afterCompletion方法，再退出拦截器链。
    2.当preHandle方法全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。然后进入拦截器链，运行所有拦截器的postHandle方法,完后从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
    当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法*/
    
    /**
     * 是否排除访问拦截验证
     * @author sizs
     * @param request
     * @return
     */
    protected boolean isExclude(HttpServletRequest request){
        String uri= request.getRequestURI();
        return this.isExclude(uri);
    }
    
    /**
     * 是否排除访问拦截验证
     * @author sizs
     * @param uri
     * @return
     */
    private boolean isExclude(String uri){
        if(excludePath == null || excludePath.trim().length()==0 ){
            return false;
        }
        else{
            String[] excludeArray = excludePath.split(",");
            for(String exclude:excludeArray){
                if(uri.contains(exclude)){
                    if(log.isDebugEnabled()){
                        log.debug("Exclude interceptor for request uri["+ uri +"]");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
