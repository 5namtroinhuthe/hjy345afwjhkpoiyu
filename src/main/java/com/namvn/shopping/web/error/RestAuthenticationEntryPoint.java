package com.namvn.shopping.web.error;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * todo sẽ xử lý những request chưa được xác thực.
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     */
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
//    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized");
    }
}