package com.example.eservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.eservice.models.entity.seguridad.UsuarioResponse;
import com.example.eservice.models.service.seguridad.IUsuarioService;

@Component
@SuppressWarnings("deprecation")
public class UserInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		try {
			
			if(!isUserLogged()) {
				throw new Exception("El usuario no se encuentra logueado");
			}
        	
	        String username = getCurrentUsername();
	        
	        if(username == null || username.trim().isBlank() || username.trim().isEmpty()) {
	        	throw new Exception("No se encontro el username");
	        }
	        
	        UsuarioResponse usuario = usuarioService.findUsuarioResponseByUsername(username);
	        
	        if(usuario == null) {
	        	throw new Exception("No se encontro al usuario");
	        }
    		
		    request.setAttribute("user_id", usuario.getId());
		    request.setAttribute("user_username", usuario.getUsername());
		    request.setAttribute("user_email", usuario.getEmail());
			
		} catch (Exception e) {
			
            request.setAttribute("user_id", 0);
            request.setAttribute("user_username", "");
            request.setAttribute("user_email", "");     
		}
		
        return true;
	}
	
	private static boolean isUserLogged() {
		try {
			return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
		} catch (Exception e) {
			return false;
		}
	}
	
	private static String getCurrentUsername() {
		String username  = "";
		
		try {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (principal instanceof UserDetails) {
			  username = ((UserDetails) principal).getUsername();
			} else {
			  username = principal.toString();
			}			
			
		} catch (Exception e) {
			
		}
		
		return username;
	}
}