package utils;

import Page.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JdbcUtils

{   Robot robot;
    int i = 0;
   public String[] response;


    public void updateDB(String sqlURL,String databaseName,String  userName,String password,WebDriver driver,  final MainPage mPage)throws Throwable
    {
        Statement statement = getConnection(sqlURL, databaseName, userName, password);
        clearTable(statement);
        addBid(statement);
        statement.close();

        robot.delay(12000);
        //Ожидание получения списка заявок в количестве 20 штук
        (new WebDriverWait(driver, 40)).until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver d)
            {return (mPage.requestList.size() == 20);}});
    }


    public Statement getConnection(String url, String database, String username, String password) throws SQLException
    {

        String connectionUrl = ""+url+";databaseName="+database+";user="+username+";password="+password+";";
        Connection con = DriverManager.getConnection(connectionUrl);

        return con.createStatement();
    }

    public void clearTable (Statement stmt) throws SQLException
    {
        stmt.executeUpdate("DELETE FROM [MVK_INBOX].[dbo].[Zayav]");
    }

    public void addBid(Statement stmt) throws Throwable
    {
        String line;
        robot = new Robot();
        long now = new java.util.Date().getTime();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");

        BufferedReader reader = new BufferedReader(new FileReader("/MVK/bid.txt"));

        while ((line = reader.readLine()) != null){

            String s =    formatter.format(now);

            String add = "INSERT INTO [MVK_INBOX].[dbo].[Zayav] ([Date]\n" +
                    "\t,[ControlCEO]\n" +
                    "      ,[ControlCDU]\n" +
                    "      ,[ControlFSO]\n" +
                    "      ,[Reason]\n" +
                    "      ,[X]\n" +
                    "      ,[Y]\n" +
                    "      ,[ogr_geometry])  VALUES ('"+s+"',"+line+")";

            stmt.executeUpdate(add);
            now =now-60000;
        }


    }

    public String[] sortRequest(Statement stmt) throws Throwable
    {
        ResultSet rs;
        response = new String[20];



        rs = getRequest(stmt,"SELECT * FROM [MVK_INBOX].[dbo].[Zayav]\n" +
                "where ControlCDU = 1 or ControlFSO = 1 or ControlCEO = 1  order by Date DESC");
        //while (rs.next()) {System.out.println(dateFormatter(rs.getTimestamp(2).getTime()));}

        getArray(rs);
        rs = getRequest(stmt," SELECT * FROM [MVK_INBOX].[dbo].[Zayav]\n" +
                "where ControlCDU = 0 and ControlFSO = 0 and ControlCEO = 0  order by Date DESC");

         getArray(rs);


        return response;
    }

    public  ResultSet getRequest(Statement stmt, String s) throws SQLException
    {
        return stmt.executeQuery(s);
    }

    public String dateFormatter (long ms) throws Throwable
    {
        DateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter1.format(ms);
    }

    public void getArray(ResultSet rs) throws Throwable
        {
            while (rs.next())
            {
                switch (rs.getInt(6))
                {
                    case 601: { response[i]= String.valueOf("Востановление трубопровода"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 602: { response[i]= String.valueOf("Засоры"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 603: { response[i]= String.valueOf("Затопление помещений насосных станций подкачки хвс"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 604: { response[i]= String.valueOf("Качество воды"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 605: { response[i]= String.valueOf("Колодцы на проезжей части"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 606: { response[i]= String.valueOf("Колодцы профилактические работы"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 607: { response[i]= String.valueOf("Колодцы"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 608: { response[i]= String.valueOf("Нарушение подачи воды"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 609: { response[i]= String.valueOf("Подтопление"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 610: { response[i]= String.valueOf("Пожар"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 611: { response[i]= String.valueOf("Превышение расчетного давления после насосов"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 612: { response[i]= String.valueOf("Провалы"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 613: { response[i]= String.valueOf("Профилактические работы"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 614: { response[i]= String.valueOf("Прочие работы"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 615: { response[i]= String.valueOf("Смена водосчетчика"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 616: { response[i]= String.valueOf("Утечка воды"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 617: { response[i]= String.valueOf("ЧС (взрыв)"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                    case 618: { response[i]= String.valueOf("Шум от работы насосных агрегатов"+", "+dateFormatter(rs.getTimestamp(2).getTime())); i++; break;}
                }
            }

    }
}
