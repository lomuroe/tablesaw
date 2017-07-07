package com.github.lwhite1.tablesaw.examples;

import com.github.lwhite1.tablesaw.api.Table;
import com.github.lwhite1.tablesaw.api.TimeColumn;
import com.google.common.base.Stopwatch;
import org.iq80.snappy.SnappyFramedOutputStream;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.lwhite1.tablesaw.api.QueryHelper.column;

/**
 * Created by lomuroe on 2017/7/6.
 */

public class JdbcQuery {

    //数据库连接地址
    private final static String URL = "jdbc:mysql://10.19.160.113:3306/algo_algorithm?useUnicode=yes&characterEncoding=UTF-8";
    //用户名
    public final static String USERNAME = "hn_longmin";
    //密码
    public final static String PASSWORD = "hn_longmin.2L9dmiNxidR9uQ7m8xwPcy1p2VG1Nh4B";
    //加载的驱动程序类（这个类就在我们导入的jar包中）
    public final static String DRIVER = "com.mysql.jdbc.Driver";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        query();
//        load();
        String fileName = "F:\\benew\\data\\test\\t.txt";

        List<Integer> floats = new ArrayList<>();
        for(int i=0;i<10000000;i++){
            floats.add(i);
        }

        try (FileOutputStream fos = new FileOutputStream(fileName);
             SnappyFramedOutputStream sos = new SnappyFramedOutputStream(fos);
             DataOutputStream dos = new DataOutputStream(sos)){
            int i = 0;
            for (int d : floats) {
                dos.writeInt(d);
                if (i % 10_000 == 0) {
                    dos.flush();
                }
                i++;
            }
            dos.flush();
        }catch(Exception ex){

        }

//        String value = "2014-12-16 00:00:00.0";
//        DateTimeFormatter selectedFormatter = TypeUtils.DATE_TIME_FORMATTER;
////        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
//        LocalDateTime time = LocalDateTime.parse(value, selectedFormatter);
//        out(time);
    }

    //TODO(lwhite): saveTable the column using integer compression
    public static void writeColumn(String fileName, TimeColumn column) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             SnappyFramedOutputStream sos = new SnappyFramedOutputStream(fos);
             DataOutputStream dos = new DataOutputStream(sos)) {
            int i = 0;
            for (int d : column.data()) {
                dos.writeInt(d);
                if (i % 10_000 == 0) {
                    dos.flush();
                }
                i++;
            }
            dos.flush();
        }
    }

    public static void load(){
        String folderPath = "F:\\benew\\data\\test\\tmp.saw";
        Stopwatch watch = Stopwatch.createStarted();
        Table t = Table.readTable(folderPath);
        t.append(t);
        t.selectWhere(column("").isAfter(LocalDate.parse("2017", DateTimeFormatter.ofPattern("yyyy"))));
        out(watch.elapsed(TimeUnit.MILLISECONDS));
        out(t.rowCount());
    }

    public static void out(Object o){
        System.out.println(o);
    }


    //方法：查询操作
    public static void query(){
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Table t =Table.create("TQ_SK_BASICINFO");
            long index = 0;
            Statement state = null;
            ResultSet rs = null;
//            while (true) {
                String sql = "select SECURITY_ID,TRADE_DATE,totalFixedAssets  from equ_factor_derive";
                state = conn.createStatement();
                Stopwatch watch = Stopwatch.createStarted();
                //执行查询并返回结果集
                rs = state.executeQuery(sql);

                out(watch.elapsed(TimeUnit.SECONDS));
                watch.reset().start();
                Table tmp = Table.create(rs,"tmp");
//                if(tmp.rowCount()==0){
//                    break;
//                }
                tmp.save("F:\\benew\\data\\test");

                out(watch.elapsed(TimeUnit.SECONDS));
                out("输出行数："+tmp.rowCount());

                index+=10000;
//            }

//            while(rs.next()){  //通过next来索引：判断是否有下一个记录
//                //rs.getInt("id"); //方法：int java.sql.ResultSet.getInt(String columnLabel) throws SQLException
//                int id = rs.getInt(1);  //方法：int java.sql.ResultSet.getInt(int columnIndex) throws SQLException
//
//                String name = rs.getString(2);
//                int age = rs.getInt(3);
//                String description = rs.getString(4);
//                System.out.println("id="+id+",name="+name+",age="+age+",description="+description);
//            }
            rs.close();
            state.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}