package com.example.eservice.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.example.eservice.models.entity.seguridad.Usuario;
import com.example.eservice.models.service.seguridad.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("id", usuario.getId());
		info.put("tipo_documento", usuario.getTipoDocumento().getAbreviatura());
		info.put("nro_documento", usuario.getNroDocumento());
		info.put("nombre", usuario.getNombre());
		info.put("estado_id", usuario.getEstado().getTablaAuxiliarDetalleId().getId());
		info.put("estado", usuario.getEstado().getAbreviatura());
		info.put("user_name", usuario.getUsername());
		info.put("email", usuario.getEmail());
		
		List<String> roles = usuario.getRoles().stream().map(role -> role.getNombreDetallado()).collect(Collectors.toList());		
		info.put("roles", roles);
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}