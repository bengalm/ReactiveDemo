package com.example.demo.uitls;

import com.example.demo.vo.Address;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import java.io.File;
import java.lang.reflect.Method;

public class IPUtil {
    public static Address getCityInfo(String ip){
    //db
    String dbPath = IPUtil.class.getResource("/ip2region.db").getPath();

    File file = new File(dbPath);

        if ( file.exists() == false ) {
        System.out.println("Error: Invalid ip2region.db file");
    }

    //查询算法
    int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
    //DbSearcher.BINARY_ALGORITHM //Binary
    //DbSearcher.MEMORY_ALGORITYM //Memory
        try {
        DbConfig config = new DbConfig();
        DbSearcher searcher = new DbSearcher(config, dbPath);

        //define the method
        Method method = null;
        switch ( algorithm )
        {
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

        dataBlock  = (DataBlock) method.invoke(searcher, ip);

            String ipstr = dataBlock.getRegion();

            ipstr=ipstr.replace("|", ",");
            if (StringUtils.isNotBlank(ipstr)) {
                Address address= getAddressVo(ipstr);
                address.setIp(ip);
               /* address.setOsName(agentGetter.getOS());
                address.setDevice(agentGetter.getDevice());*/
                return address;
            }
    } catch (Exception e) {
        e.printStackTrace();
    }

        return null;
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

}