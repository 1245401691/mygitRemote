package cn.wolfcode.shop.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.wolfcode.shop.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class UserUtil {
	
	private static void createUser(int count) throws Exception{
		List<User> users = initUser(count);
//		//插入数据库
//		insertToDb(users);
		//登录，生成token
		creatToken(users);
	}
	public static void main(String[] args)throws Exception {
		createUser(1000);
	}

	private static void creatToken(List<User> users) throws IOException {
		String urlString = "http://localhost/login";
		File file = new File("D:/tokens.txt");
		if(file.exists()) {
			file.delete();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		file.createNewFile();
		raf.seek(0);
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			URL url = new URL(urlString);
			HttpURLConnection co = (HttpURLConnection)url.openConnection();
			co.setRequestMethod("POST");
			co.setDoOutput(true);
			OutputStream out = co.getOutputStream();
			String params = "mobile="+user.getId()+"&password="+ Md5Util.inputPassToFormPass("111111");
			out.write(params.getBytes());
			out.flush();
			InputStream inputStream = co.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte buff[] = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buff)) >= 0) {
				bout.write(buff, 0 ,len);
			}
			inputStream.close();
			bout.close();
			String response = new String(bout.toByteArray());
			JSONObject jo = JSON.parseObject(response);
			String token = jo.getString("data");
			System.out.println("create token : " + user.getId());

			String row = user.getId()+","+token;
			raf.seek(raf.length());
			raf.write(row.getBytes());
			raf.write("\r\n".getBytes());
		}
		raf.close();
	}

	private static List<User> initUser(int count) {
		List<User> users = new ArrayList<User>(count);
		//生成用户
		for(int i=0;i<count;i++) {
			User user = new User();
			user.setId(13000000000L+i);
			user.setLoginCount(1);
			user.setNickname("user"+i);
			user.setRegisterDate(new Date());
			user.setSalt("4c3b2a1");
			user.setPassword(Md5Util.formPassToDbPass(Md5Util.inputPassToFormPass("111111"),user.getSalt()));
			users.add(user);
		}
		System.out.println("create user");
		return users;
	}

	private static void insertToDb(List<User> users) throws Exception {
		Connection conn = DBUtil.getConn();
		String sql = "insert into t_user(login_count, nickName, register_date, salt, password, id)values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			pstmt.setInt(1, user.getLoginCount());
			pstmt.setString(2, user.getNickname());
			pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
			pstmt.setString(4, user.getSalt());
			pstmt.setString(5, user.getPassword());
			pstmt.setLong(6, user.getId());
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		pstmt.close();
		conn.close();
		System.out.println("insert to db");
	}


}
