package store.idragon.tool.rest;
import org.springframework.web.servlet.HandlerInterceptor;
import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.check.ParamCheckUtils;
import store.idragon.tool.base.exception.IDragonException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author xiaoshimei0305
 * date  2020/12/26 10:13 下午
 * description
 * @version 1.0
 */
public class TokenInterceptor<T> implements HandlerInterceptor{
    /**
     * token 前端key管理对象
     */
    private String tokenKey;
    /**
     * token 管理
     */
    private TokenManager<T> tokenManager;

    public TokenInterceptor(TokenManager<T> tokenManager) {
        this(null,tokenManager);
    }

    public TokenInterceptor(String tokenKey,TokenManager<T> tokenManager) {
        this.tokenKey = tokenKey;
        this.tokenManager=tokenManager;
        ParamCheckUtils.notNull(this.tokenManager,"Token管理");
        if(StringUtils.isBlank(this.tokenKey)){
            this.tokenKey="token";
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        T data = null;
        try {
            String token=request.getHeader(this.tokenKey);
            ParamCheckUtils.notNull(token,"令牌");
            data = tokenManager.validToken(token);
            if(data==null){
                throw new IDragonException(LoginResultEnum.FAILED_LOGIN_CHECK,"未读取到令牌数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.responseWhenException(e,response);
            return false;
        }
        return true;
    }
}
