package com.example.demo.uitls;

/*import com.example.demo.vo.Address;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Method;*/

/*@Slf4j*/
public class IPUtilM {
	/*public static Address getCityInfo(String ip, UserAgentGetter agentGetter, String dbPath) {
		//String dbPath = IPUtil.class.getResource("/ip2region.db").getPath();
		log.info("dbPath:"+dbPath);
		File file = new File(dbPath);
		if (file.exists() == false) {
			log.error("Error: Invalid ip2region.db file");
		}
		int algorithm = DbSearcher.BTREE_ALGORITHM;
		try {
			DbConfig config = new DbConfig();
			DbSearcher searcher = new DbSearcher(config, dbPath);
			Method method = null;
			switch (algorithm) {
			case DbSearcher.BTREE_ALGORITHM:
				method = searcher.getClass().getMethod("btreeSearch", String.class);
				break;
			case DbSearcher.BINARY_ALGORITHM:
				method = searcher.getClass().getMethod("binarySearch", String.class);
				break;
			case DbSearcher.MEMORY_ALGORITYM:
				method = searcher.getClass().getMethod("memorySearch", String.class);
				break;
			}
			DataBlock dataBlock = null;
			if (!Util.isIpAddress(ip)) {
				System.out.println("Error: Invalid ip address");
			}
			dataBlock = (DataBlock) method.invoke(searcher, ip);
			String ipstr=dataBlock.getRegion();
			log.info("解析IP地址 : "+ipstr);
			ipstr=ipstr.replace("|", ",");
			if (StringUtils.isNotBlank(ipstr)) {
				Address address= getAddressVo(ipstr);
				address.setIp(ip);
				address.setOsName(agentGetter.getOS());
				address.setDevice(agentGetter.getDevice());
				return address;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Address getIpAddress(HttpServletRequest request, HttpServletResponse response, String ip2regionFilePath) {
		UserAgentGetter agentGetter = new UserAgentGetter(request);
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.contains(",")) {
				ip = ip.split(",")[0];
			}
		}
		log.info("取到ip: "+ip);
		return getCityInfo(ip,agentGetter,ip2regionFilePath);
	}

	public static Address getAddressVo(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		String[] strArr = str.split(",");
		Address address = new Address();

		if (!strArr[0].equals("0")) {
			address.setCountry(strArr[0]);
		}
		if (!strArr[2].equals("0")) {
			address.setProvince(strArr[2]);
		}
		if (!strArr[3].equals("0")) {
			address.setCity(strArr[3]);
		}
		return address;
	}

	public static void main(String[] args) throws Exception {
		System.err.println(getCityInfo("116.136.20.163",null,"D:\\ip2region\\ip2region-2.0.0-release\\data\\ip2region.db"));
		System.err.println(getCityInfo("116.136.20.163",null,"D:/ip2region/ip2region.db"));
		//System.err.println(getCityInfo("103.112.241.243",null,"D:/ip2region/ip2region.db"));
		
		
		
		
	}
*/}
