package com.example.eservice.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.eservice.VariablesGlobales;

public class ExcelAprobaciones {
	public static void generarProtocolo(List<Map<String, Object>> aprobaciones) throws IOException {
		
		String fecha = aprobaciones.get(0).get("fecha").toString();
		
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Pedidos");
		
		CellStyle centradoTitulo = workbook.createCellStyle();
		centradoTitulo.setAlignment(HorizontalAlignment.CENTER);
		Font fontT = workbook.createFont();
		fontT.setFontHeightInPoints((short) 12);
		centradoTitulo.setFont(fontT);
		
		CellStyle titFirstRowG = workbook.createCellStyle();
		Font font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 12);
		titFirstRowG.setFont(font1);
		
		Row cab = sheet.createRow(0);
		Cell c1 = cab.createCell(0);
		c1.setCellValue("Serv. Socios / Fecha");
		c1.setCellStyle(titFirstRowG);
        
        CellRangeAddress mrPedido  = new CellRangeAddress(0, 0, 1, 13);		
		Cell c2 = cab.createCell(1);
		c2.setCellValue("PEDIDO");
		sheet.addMergedRegion(mrPedido);
        
        CellRangeAddress mrRecojo  = new CellRangeAddress(0, 0, 14, 18);
		Cell c3 = cab.createCell(14);
		c3.setCellValue("Hoja de Recojos");
		sheet.addMergedRegion(mrRecojo);
		
		String cabeceras[] = new String[] { "Proveedor", "Fecha", "Precio", "Monto", "Medio.P", "Motorizado 1", "Motorizado 2", "Estado", " Cliente", "Número", "Distrito", "Dirección / referencia / Producto / Observación de entrega", " GPS", "Observaciones de incidencias", "Nombre / Negocio", "TLF", "Distrito R", "Dirección", "Referencia" };
		String campos[] = new String[] { "proveedor", "fecha", "precio", "precio", "medio_pago", "motorizado", "motorizado2", "estado", "cliente", "numero", "distrito", "datos", "gps", "obs", "negocio", "tlf", "distrito_recojo", "direccion_recojo", "referencia_recojo" };
		
		List<String> titulos = Arrays.asList(cabeceras);
		List<String> camps = Arrays.asList(campos);
		
		Row tit = sheet.createRow(1);
		
		for(String titulo: titulos) {
			Cell tCell = tit.createCell(titulos.indexOf(titulo));
			tCell.setCellValue(titulo);
			tCell.setCellStyle(centradoTitulo);
		}
		
		for(int i = 0; i < aprobaciones.size(); i++) {
			
			Row row = sheet.createRow(i+2);
			
			for (int j = 0; j < camps.size(); j++) {
				//System.out.println(i + ", " + j + ", " + camps.get(j).toString());
				Cell cell = row.createCell(j);
				cell.setCellValue(aprobaciones.get(i).get(camps.get(j).toString()).toString());			
				
			}
			
		}
		
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3200);
		sheet.setColumnWidth(2, 2400);
		sheet.setColumnWidth(3, 2400);
		sheet.setColumnWidth(4, 2400);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 10800);
		sheet.setColumnWidth(9, 3200);
		sheet.setColumnWidth(10, 3200);
		sheet.setColumnWidth(11, 17200);
		sheet.setColumnWidth(12, 11600);
		sheet.setColumnWidth(13, 11600);
		sheet.setColumnWidth(14, 8000);
		sheet.setColumnWidth(15, 3200);
		sheet.setColumnWidth(16, 4000);
		sheet.setColumnWidth(17, 13200);
		sheet.setColumnWidth(18, 11600);
		
		FileOutputStream outputStream = new FileOutputStream(new File(VariablesGlobales.APROBACIONES_DIARIAS + "/REPORTE_APROBACION_" + fecha +  ".xlsx"));
		workbook.write(outputStream);
        workbook.close();
	}
}