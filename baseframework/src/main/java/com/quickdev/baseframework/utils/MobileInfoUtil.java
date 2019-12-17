package com.quickdev.baseframework.utils;

import android.util.Log;


import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MobileInfoUtil {

    public static String getMacAddress() throws SocketException {
        String F_TRONKEY = "f_TronKey";//file name
        String address = "";
//                (String) SpUtils.getParam(F_TRONKEY, AppContextUtil.getContext(),
//                        AppContextUtil.getContext().getString(R.string.mac_address), "");
//        if (!StringTronUtil.isEmpty(address)) return address;

        // 把当前机器上的访问网络接口的存入 Enumeration集合中
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        if (interfaces != null) {
            while (interfaces.hasMoreElements()) {
                NetworkInterface netWork = interfaces.nextElement();
                // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : by) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                String mac = builder.toString();
                Log.d("mac", "interfaceName=" + netWork.getName() + ", mac=" + mac);
                // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
                if (netWork.getName().equals("wlan0")) {
                    Log.d("mac", " interfaceName =" + netWork.getName() + ", mac=" + mac);
                    address = mac;
                }
            }
//            SpUtils.setParam(F_TRONKEY, AppContextUtil.getContext(), AppContextUtil.getContext().getString(R.string.mac_address), address);
        }
        return address;
    }
}
