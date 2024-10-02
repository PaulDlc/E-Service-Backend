package com.example.eservice.socket.entregas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.eservice.models.entity.maestros.Cliente;
import com.example.eservice.models.entity.maestros.Notificacion;
import com.example.eservice.models.service.maestros.IClienteService;
import com.example.eservice.models.service.maestros.INotificacionService;
import com.example.eservice.models.service.seguridad.IUsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WebSocketEntregaController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private INotificacionService notificacionService;
	
	@Autowired
	private IClienteService clienteService;
	
	@MessageMapping("/sendMessage/update-list-tienda/{idTienda}")
	public void sendNotificationAddProduct(@DestinationVariable String idTienda) {
		String response = "La tienda a agregado un nuevo producto.";
		messagingTemplate.convertAndSend("/eservice/notification/update-list-tienda/" + idTienda, response);
	}
	
	@MessageMapping("/sendMessage/ayuda-tienda/{idTienda}")
    @SendTo("/eservice/messages/ayuda-tienda")
    public String sendMessageAyudaTienda(String notificacion, @DestinationVariable String idTienda) {
		ObjectMapper convert= new ObjectMapper();
		
        try {
        	Integer id = Integer.parseInt(idTienda);
        	System.out.println(id);
			Notificacion noti = convert.readValue(notificacion, Notificacion.class);
        	
        	Cliente cliente = clienteService.getClienteByUserId(id);
			noti.setIdUsuario(cliente.getUsuario());
			notificacionService.save(noti);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return "";
    }
	
	@MessageMapping("/sendMessage/ayuda")
    @SendTo("/eservice/messages/ayuda")
    public String sendMessageAyuda(String notificacion) {
		ObjectMapper convert= new ObjectMapper();
        try {
        	List<Integer> idsUsuario = usuarioService.getAyuda();
        	
        	for (Integer id: idsUsuario) {
    			Notificacion noti = convert.readValue(notificacion, Notificacion.class);
    			noti.setIdUsuario(id);
    			notificacionService.save(noti);        		
        	}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        return "";
    }
	
	@MessageMapping("/sendMessage")
    @SendTo("/eservice/messages")
    public void sendMessage(String notificacion) {
		ObjectMapper convert= new ObjectMapper();
        try {
			Notificacion noti = convert.readValue(notificacion, Notificacion.class);
			notificacionService.save(noti);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }
}
