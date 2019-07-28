package guru.springframework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutSuccessHandlerImpl extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class.getName());

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {


        if (authentication != null) {
            PrincipalImpl build = new PrincipalImpl.PrincipalImplBuilder()
                    .withLogin(authentication.getName())
                    .withRoles(authentication.getAuthorities())
                    .build();
            logger.info("Logged out - " + build);
            super.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
        } else {
            ServletContext servletContext = httpServletRequest.getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/login?user=anonymous");
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
    }
}
