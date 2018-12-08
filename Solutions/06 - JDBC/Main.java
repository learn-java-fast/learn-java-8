package com.example.mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		try (Connection con = DriverManager.getConnection("jdbc:derby:myDB;create=true")) {
			try (Statement dropTbl = con.createStatement()) {
				boolean droptbl = dropTbl.execute("DROP TABLE BOOK");
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
			try (Statement createTbl = con.createStatement()) {
				boolean created = createTbl.execute("create table book (id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT "
						+ "PEOPLE_PK PRIMARY KEY, ISBN varchar(25), NAME varchar(200), DESCRIPTION varchar(200), AUTHOR varchar(200))");
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
			try (PreparedStatement insertTbl = con.prepareStatement("insert into BOOK (ISBN, NAME, DESCRIPTION, AUTHOR) values(?,?,?,?)" )) {
				insertTbl.setString(1, "978-0-7172-6059-1");
				insertTbl.setString(2, "The Cat in the Hat");
				insertTbl.setString(3, "Some description");
				insertTbl.setString(4, "Dr. Seuss");
				insertTbl.executeUpdate();
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
			try (Statement stmt = con.createStatement()) {
				ResultSet rs = stmt.executeQuery("select name from book");
				while (rs.next()) {
					String value = rs.getString(1);
					logger.info("row " + value);
				}
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
			logger.info("done");
		} catch(SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			
		}
		// Now we do the same thing using the RowSet
		try {
			RowSetFactory myRowSetFactory = RowSetProvider.newFactory();
			FilteredRowSet frs = myRowSetFactory.createFilteredRowSet();
			frs.setCommand("SELECT NAME, AUTHOR, DESCRIPTION, ISBN FROM BOOK");
			frs.setUrl("jdbc:derby:myDB;create=true");
			frs.execute();
			frs.moveToInsertRow();
			frs.updateString("NAME", "Green Eggs and Ham");
			frs.updateString("AUTHOR", "Dr. Seuss");
			frs.updateString("DESCRIPTION", "Good Book about green eggs and ham");
			frs.updateString("ISBN", "B00ESF277M");
			frs.insertRow();
			frs.moveToCurrentRow();
			frs.acceptChanges();
			frs.moveToInsertRow();
			frs.updateString("NAME", "Charlotte's Web");
			frs.updateString("AUTHOR", "E. B. White");
			frs.updateString("DESCRIPTION", "Good Book about a spider, a girl and a pig.");
			frs.updateString("ISBN", "9780064400558");
			frs.insertRow();
			frs.moveToCurrentRow();
			frs.acceptChanges();
			frs.beforeFirst();
			String[] authors = {"Dr. Seuss"};
			frs.setFilter(new AuthorFilter(authors, "AUTHOR"));
			while (frs.next()) {
				String name = frs.getString("NAME");
				logger.info("Name: "+name);
			}
			frs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}