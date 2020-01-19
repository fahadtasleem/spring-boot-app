package org.fahad.spring.core.request;

import org.springframework.util.StringUtils;

public class UserContextProvider {
    final static ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();
    //InheritableThreadLocal
    private UserContextProvider(){

    }

    public static void set(UserContext userContext){
        userContextThreadLocal.set(userContext);
    }

    public static UserContext get(){
        return userContextThreadLocal.get();
    }

    public static void unsetContext() {
        userContextThreadLocal.remove();
    }

    public static String getTenantId(){
        if(userContextThreadLocal.get() == null){
            return "";
        }
        return userContextThreadLocal.get().getTenantId();
    }
}
