package com.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// プロジェクトのルートディレクトリを取得する
	private static final String ROOT_DIR = System.getProperty("user.dir");

	// DBファイルのパス設定
	private static final File DB_FILE = new File(ROOT_DIR, "data/test.db");
	private static final String URL = "jdbc:sqlite:" + DB_FILE.getAbsolutePath();
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// ディレクトリが存在しない場合は作成する
		if (!DB_FILE.getParentFile().exists()) {
			DB_FILE.getParentFile().mkdirs();
		}
		
		// データベースファイルが存在しない場合に作成する
		if (!DB_FILE.exists()) {
			// 空のデータベースファイルを作成する
			try (Connection conn = DriverManager.getConnection(URL)) {
				System.out.println("データベースファイルが作成されました: " + DB_FILE.getAbsolutePath());
			} catch (SQLException e) {
					System.out.println("データベースファイルの作成エラー: " + e.getMessage());
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL);
		}
}