package hey.interceptor;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.Map;


public class AdminInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception{
        Map<String, Object> session =invocation.getInvocationContext().getSession();


        if(session.get("loggedin") == null  || !((boolean) session.get("loggedin")) ){
            return "login";
        }
        else if(((String)session.get("username")).equals("admin")){
            return invocation.invoke();
        }
        else{
            return "login";
        }
    }
}