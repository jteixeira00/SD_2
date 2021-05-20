package hey.interceptor;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.Map;


public class UserInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception{
        Map<String, Object> session =invocation.getInvocationContext().getSession();


        if(session.get("loggedin") == null  || !((boolean) session.get("loggedin"))){
            return "login";
        }
        else{
            return invocation.invoke();
        }
    }
}
