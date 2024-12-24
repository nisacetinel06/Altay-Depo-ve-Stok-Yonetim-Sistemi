package com.example.proje;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Connection.httpService;
import com.example.proje.Model.Calisan;

public class DepoYonetimiApplication {

		public static void main(String[] args) {
			// Veritabanı bağlantısını test etme
			String dbUrl = "jdbc:mysql://localhost:3306/depo_yonetimi";
			String username = "root";
			String password = "112606";

			DBconnection dbConnection = new DBconnection(dbUrl, username, password);

			try {
				// Bağlantıyı oluştur ve test et
				dbConnection.createConnection();

				// HTTP sunucusunu başlat
				httpService service = new httpService();
				service.createServer(); // HTTP sunucusunun oluşturulması ve başlatılması
				System.out.println("StokTakipProjesiApplication başlatıldı.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Uygulama başlatılırken hata oluştu.");
			} finally {
				// Veritabanı bağlantısını kapat
				dbConnection.closeConnection();
			}


		}
	}
