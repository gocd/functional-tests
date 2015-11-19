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

package com.thoughtworks.cruise.ssl;


import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.Enumeration;

public class AuthSSLProtocolSocketFactory implements SecureProtocolSocketFactory {
    private static final Log LOG = LogFactory.getLog(AuthSSLProtocolSocketFactory.class);

    private final AuthSSLX509TrustManagerFactory trustManagerFactory;
    private final AuthSSLKeyManagerFactory keyManagerFactory;
    private SSLContext sslcontext = null;

    /* Use file based constructor */
    @Deprecated
    public AuthSSLProtocolSocketFactory(
            AuthSSLX509TrustManagerFactory trustManagerFactory, AuthSSLKeyManagerFactory keyManagerFactory) {
        super();
        this.trustManagerFactory = trustManagerFactory;
        this.keyManagerFactory = keyManagerFactory;
    }

    public AuthSSLProtocolSocketFactory(File agentTrustFile, File agentCertificateFile, String agentStorePassword, KeyStoreManager keyStoreManager) {
        super();
        this.trustManagerFactory = new AuthSSLX509TrustManagerFactory(agentTrustFile, agentStorePassword);
        this.keyManagerFactory = new AuthSSLKeyManagerFactory(agentCertificateFile, agentStorePassword, keyStoreManager);
    }

    public void registerAsHttpsProtocol() {
        Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory) this, 443));
    }

    SSLContext getSSLContext() {
        if (this.sslcontext == null || isEmptyKeyStore()) {
            this.sslcontext = createSSLContext();
        }
        return this.sslcontext;
    }

    private SSLContext createSSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            KeyManager[] keyManagers = keyManagerFactory == null ? null : keyManagerFactory.keyManagers();
            TrustManager[] trustManagers = trustManagerFactory == null ? null : trustManagerFactory.trustManagers();
            context.init(keyManagers, trustManagers, null);
            return context;
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage(), e);
            throw new AuthSSLInitializationError("Unsupported algorithm exception: " + e.getMessage());
        } catch (GeneralSecurityException e) {
            LOG.error(e.getMessage(), e);
            throw new AuthSSLInitializationError("Key management exception: " + e.getMessage());
        }
    }

    private boolean isEmptyKeyStore() {
        SSLSessionContext sessionContext = sslcontext.getClientSessionContext();
        @SuppressWarnings("unchecked") Enumeration<byte[]> sessionIds = sessionContext.getIds();
        if (sessionIds.hasMoreElements()) {
            byte[] sessionId = sessionIds.nextElement();
            Certificate[] localCertificates = sessionContext.getSession(sessionId).getLocalCertificates();
            return localCertificates == null || localCertificates.length > 0;
        } else {
            return true;
        }
    }

    /**
     * Attempts to get a new socket connection to the given host within the given time limit.
     * <p>
     * To circumvent the limitations of older JREs that do not support connect timeout a
     * controller thread is executed. The controller thread attempts to create a new socket
     * within the given limit of time. If socket constructor does not return until the
     * timeout expires, the controller terminates and throws an
     * {@link org.apache.commons.httpclient.ConnectTimeoutException}
     * </p>
     *
     * @param host   the host name/IP
     * @param port   the port on the host
     * @param params {@link org.apache.commons.httpclient.params.HttpConnectionParams Http connection parameters}
     * @return Socket a new socket
     * @throws java.io.IOException if an I/O error occurs while creating the socket
     */
    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort,
                               final HttpConnectionParams params)
            throws IOException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        SocketFactory socketfactory = getSSLContext().getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteaddr = new InetSocketAddress(host, port);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
            return socket;
        }
    }

    /**
     * @see org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory#createSocket(String,int,java.net.InetAddress,int)
     */
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
            throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    /**
     * @see org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory#createSocket(String,int)
     */
    public Socket createSocket(String host, int port) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    /**
     * @see org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory#createSocket(java.net.Socket, String,int,boolean)
     */
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
            throws IOException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }
}

class AuthSSLInitializationError extends Error {

    /**
     * Creates a new AuthSSLInitializationError.
     */
    public AuthSSLInitializationError() {
        super();
    }

    /**
     * Creates a new AuthSSLInitializationError with the specified message.
     *
     * @param message error message
     */
    public AuthSSLInitializationError(String message) {
        super(message);
    }
}