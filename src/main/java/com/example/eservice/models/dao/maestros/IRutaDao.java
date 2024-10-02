package com.example.eservice.models.dao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.maestros.Ruta;

public interface IRutaDao extends JpaRepository<Ruta, Integer> {
	@Query(value="SELECT id FROM mae_ruta_distrito WHERE distrito_id = ?1", nativeQuery=true)
	List<Integer> getIdsByDistrito(Integer distritoId);
	
	@Query("SELECT r FROM Ruta r WHERE r.id IN( ?1 )")
	List<Ruta> getListRutaByIds(List<Integer> ids);
	
	@Query("SELECT r FROM Ruta r WHERE r.id = ?1")
	Ruta getById(Integer id);
	
	@Query(value="SELECT distrito_id FROM mae_ruta_distrito WHERE ruta_id = ?1", nativeQuery=true)
	List<Integer> getDistritosIdFromRuta(Integer rutaId);
	
	@Query(value="EXEC ruta_asignacion_distrito @distrito_id = ?1 , @ruta_id = ?2", nativeQuery=true)
	void asignarDistritoRuta(Integer distritoId, Integer rutaId);
	
	@Query(value="EXEC delete_ruta @ruta_id = ?1", nativeQuery=true)
	void deleteRuta(Integer rutaId);
}