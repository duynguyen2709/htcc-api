package htcc.gateway.service.component.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import htcc.common.constant.Constant;
import htcc.common.util.StringUtil;
import htcc.gateway.service.service.authentication.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Zuul Request Pre-filter to add additional info to request
 */
@Component
@Log4j2
public class RequestPreFilter extends ZuulFilter {

    @Autowired
    private AuthenticationService authService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        /*
            Add Request Time
         */
        ctx.getRequest().setAttribute(Constant.REQUEST_TIME, System.currentTimeMillis());

        /*
            Add Username to Header to pass to modules
         */
        Authentication auth = authService.getAuthentication();
        if (auth != null) {
            ctx.addZuulRequestHeader(Constant.USERNAME, StringUtil.valueOf(auth.getName()));
        }

        return null;
    }
}
