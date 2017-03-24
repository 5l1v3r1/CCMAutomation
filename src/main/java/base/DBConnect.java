package base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBConnect {
	Connection connection;
	private Statement statement;

	public Connection getConnection() throws SQLException,
			ClassNotFoundException, FileNotFoundException, IOException {

		String driverName = "oracle.jdbc.driver.OracleDriver";
		Class.forName(driverName);
		//String configFileLocation = String.format("%s%ssrc%smain%sresources%sconfig.properties", System.getProperty("user.dir"), File.separator, File.separator, File.separator, File.separator);

		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		String server = prop.getProperty("server");
		String user = prop.getProperty("dbuser");
		String password = prop.getProperty("dbpassword");
		String url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST="
				+ server
				+ ")(PORT=1521)) (CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=TLRA)))";
		connection = DriverManager.getConnection(url, user, password);

		return connection;

	}

	public String sifreAl() throws SQLException {
		String sifre = "";
		String selectQuery = "select substr(to_char(pl_out),instr(to_char(pl_out),'ifreniz ')+7 ,6) sifre from gnl_ext_syst_call_log where oper_name='sendSMS' and cdate >sysdate-(10/(24*60))and to_char(pl_out) like '%5%' order by gnl_ext_syst_call_log_id desc";
		Statement stmt = null;
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(selectQuery);

		if (rs.next()) {
			sifre = rs.getString("sifre");
			// System.out.println(sifre);
		}

		return sifre;
	}

	public ResultSet getAllResult(String query) throws SQLException {
		statement = connection.createStatement();
		ResultSet res = statement.executeQuery(query);
		return res;

	}

	// Gönderilen queryinin ilk rowunu geriye dönderir
	public ResultSet getFirstRowResult(String query) throws SQLException {
		statement = connection.createStatement();
		statement.setMaxRows(1);
		ResultSet res = statement.executeQuery(query);
		return res;
	}

	// Gönderilen queryinin ilk rowunun string typeli colonunu gönderir
	public String getFirstRowResultCellValue(String query, String columnName)
			throws SQLException {
		statement = connection.createStatement();
		statement.setMaxRows(1);
		ResultSet res = statement.executeQuery(query);
		res.next();

		return res.getString(columnName);
	}

	// Gönderilen queryinin ilk rowunun int type li colonunun degerini gönderir
	public int getFirstRowResultCellValue(String query, int columnName)
			throws SQLException {
		statement = connection.createStatement();
		statement.setMaxRows(1);
		ResultSet res = statement.executeQuery(query);
		res.next();

		return res.getInt(columnName);
	}

	public void disConnect() throws SQLException {

		connection.close();
	}
	
	public int musteriHesapSifirlama(String musteri) throws SQLException{		
		String query="update prod set st_id=9009 where prod_id in( select prod_id from v_customer_account_prod_inf where ext_cust_id='"+musteri+"')";
		statement=connection.createStatement();
		boolean a=statement.execute(query);	
		if(a==true)
		{
			return 0;
		}
		else
			return 1;
	}
	//delete from addr where CONDO_UNIT_ID=2343211
	
	
	public void BBKSil() throws SQLException{		
		String query="delete addr where row_id=2194020";
		statement=connection.createStatement();
		statement.execute(query);	
	}
	
	public void tatilHat3ayKurali() throws SQLException{		
	String query="update cust_ord_item tt set tt.is_slct = 0 where tt.cust_ord_item_id in (select coi.cust_ord_item_id from cust_ord_item coi, bsn_inter bi, bsn_inter_spec bis where coi.bsn_inter_id=bi.bsn_inter_id and bi.bsn_inter_spec_id=bis.bsn_inter_spec_id  and coi.is_slct=1 and bis.shrt_code in (SELECT * FROM TABLE(CAST (in_varchar2_list ('ACT_CANCEL') AS varchar2_table)))  and coi.cust_id = 136817822)";
		//String query="update cust_ord_item tt set tt.is_slct = 0 where tt.cust_ord_item_id in (select coi.cust_ord_item_id from cust_ord_item coi, bsn_inter bi, bsn_inter_spec bis where coi.bsn_inter_id=bi.bsn_inter_id and bi.bsn_inter_spec_id=bis.bsn_inter_spec_id  and coi.is_slct=1 and bis.shrt_code in (SELECT * FROM TABLE(CAST (in_varchar2_list ('ACT_CANCEL') AS varchar2_table)))  and coi.cust_id = 136818598)";

		statement=connection.createStatement();
		statement.execute(query);
		
	}
	
	public void wiroKural() throws SQLException{		
	//	String query="update cust_ord_item tt set tt.is_slct = 0 where tt.cust_ord_item_id in (select coi.cust_ord_item_id from cust_ord_item coi, bsn_inter bi, bsn_inter_spec bis where coi.bsn_inter_id=bi.bsn_inter_id and bi.bsn_inter_spec_id=bis.bsn_inter_spec_id  and coi.is_slct=1 and bis.shrt_code in (SELECT * FROM TABLE(CAST (in_varchar2_list ('ACT_CANCEL') AS varchar2_table)))  and coi.cust_id = 136817822)";
		String query="update gnl_char set derv_frml = null where shrt_code = 'MOVE_SIGNED' ";

		statement=connection.createStatement();
		statement.execute(query);
		
	}
	
	public void havuzMVexecute() throws SQLException
	{
		
		String command = "{call ExecuteHavuzMV}"; 
		CallableStatement cstmt = connection.prepareCall (command);
		cstmt.execute();

	}

public void tatilHat3ayKurali1() throws SQLException{		
	String query="update cust_ord_item tt set tt.is_slct = 0 where tt.cust_ord_item_id in (select coi.cust_ord_item_id from cust_ord_item coi, bsn_inter bi, bsn_inter_spec bis where coi.bsn_inter_id=bi.bsn_inter_id and bi.bsn_inter_spec_id=bis.bsn_inter_spec_id  and coi.is_slct=1 and bis.shrt_code in (SELECT * FROM TABLE(CAST (in_varchar2_list ('ACT_CANCEL') AS varchar2_table)))  and coi.cust_id in('8655074','136818598'))";
	statement=connection.createStatement();
	statement.execute(query);
	
}

public void tatilHat3ayKurali2() throws SQLException{		
	String query="update cust_ord_item tt set tt.is_slct = 0 where tt.cust_ord_item_id in (select coi.cust_ord_item_id from cust_ord_item coi, bsn_inter bi, bsn_inter_spec bis where coi.bsn_inter_id=bi.bsn_inter_id and bi.bsn_inter_spec_id=bis.bsn_inter_spec_id  and coi.is_slct=1 and bis.shrt_code in (SELECT * FROM TABLE(CAST (in_varchar2_list ('ACT_CANCEL') AS varchar2_table)))  and coi.cust_id in('8655074','136818598'))";
	statement=connection.createStatement();
	statement.execute(query);

}

public void tatilHat3ayKurali3() throws SQLException{		
	String query="update cust_ord_item tt set tt.is_slct = 0 where tt.cust_ord_item_id in (select coi.cust_ord_item_id from cust_ord_item coi, bsn_inter bi, bsn_inter_spec bis where coi.bsn_inter_id=bi.bsn_inter_id and bi.bsn_inter_spec_id=bis.bsn_inter_spec_id  and coi.is_slct=1 and bis.shrt_code in (SELECT * FROM TABLE(CAST (in_varchar2_list ('ACT_CANCEL') AS varchar2_table)))  and coi.cust_id in('8655074','136818598'))";
	statement=connection.createStatement();
	statement.execute(query);

}

	


	
	
	public List numaraEkleCikar(long siparisNo) throws SQLException
	{
	
		String query="select distinct A.CONTN_PBLSH_JOB_SUMM_ID as "
				+ "Container_Transaction_Id , B.TXN_ID as Container_Sub_Transaction_Id from"
				+ " CONTN_PBLSH_JOB_SUMM a , CONTN_PBLSH_JOB_ITEM b "
				+ "where A.CONTN_PBLSH_JOB_SUMM_ID=B.CONTN_PBLSH_JOB_SUMM_ID and A.CUST_ORD_ID = ?";
        List<String> list = new ArrayList<String>();
        PreparedStatement  preparedStatement = connection.prepareStatement(query);
		preparedStatement.setLong(1, siparisNo);

		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {

			String col1 = rs.getString("Container_Transaction_Id");
			String col2= rs.getString(2);
			//int col2 = rs.getInt(1);

			list.add(col1);
			list.add(col2);
			


		}
		
		return list;
		
	}
	
	public void lockSilme() throws SQLException
	{
		
		String query="TRUNCATE TABLE CUST_ORD_LOCK";
		statement=connection.createStatement();
		statement.execute(query);
	}
}
