/*************************GO-LICENSE-START*********************************
 * Copyright 2015 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.cruise.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemUtil {
    private static final Logger LOG = Logger.getLogger(SystemUtil.class);


    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.indexOf("Windows") > -1;
    }

    public static String getLocalhostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFirstLocalNonLoopbackIpAddress() {
        try {
            SortedSet<String> addresses = new TreeSet<String>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress address = inetAddressEnumeration.nextElement();
                    if (!address.isLoopbackAddress() && !address.getHostAddress().contains(":")) {
                        addresses.add(address.getHostAddress());
                    }
                }
            }
            if (addresses.isEmpty()) {
                throw new RuntimeException("Failed to get non-loopback local ip address!");
            }
            return addresses.first();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isLocalIpAddress(String ipAddress) {
        try {
            return isLocalLoopbackIpAddress(ipAddress) || isLocalNonLoopbackIpAddress(ipAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isLocalPortOccupied(int portNum) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(portNum));
            return s.isConnected();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isLocalLoopbackIpAddress(String ipAddress) {
        return ipAddress.startsWith("127.");
    }

    public static boolean isLocalhost(String hostName, String ipAddress) {
        try {
            return isLocalhostWithLoopbackIpAddress(hostName, ipAddress) || isLocalNonLoopbackIpAddress(ipAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isLocalhostWithLoopbackIpAddress(String hostName, String ipAddress) throws Exception {
        String realHostName = InetAddress.getLocalHost().getHostName();
        return realHostName.equalsIgnoreCase(hostName) && isLocalLoopbackIpAddress(ipAddress);
    }

    public static boolean isLocalNonLoopbackIpAddress(String ipAddress) throws Exception {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
            while (inetAddressEnumeration.hasMoreElements()) {
                InetAddress address = inetAddressEnumeration.nextElement();
                if (!address.isLoopbackAddress() && ipAddress.equalsIgnoreCase(address.getHostAddress())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isLocalhostReachable(int port) {
        return reachable(null, port);
    }

    public static boolean reachable(String name, int port) {
        Socket s = null;
        try {
            s = new Socket(InetAddress.getByName(name), port);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            closeQuietly(s);
        }
    }

    private static void closeQuietly(Socket s) {
        if (s == null) {
            return;
        }
        try {
            s.close();
        } catch (IOException e) {
            LOG.info("failed to close socket", e);
        }
    }

    public static String currentWorkingDirectory() {
        String location;
        File file = new File(".");
        try {
            location = file.getCanonicalPath();
        } catch (IOException e) {
            location = file.getAbsolutePath();
        }
        return location;

    }
}
