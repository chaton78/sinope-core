/**
 * Copyright (c) 2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Creator: Pascal Larin
 * https://github.com/chaton78
 */
package ca.tulip.sinope.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import ca.tulip.sinope.core.appdata.SinopeLocalTimeData;
import ca.tulip.sinope.core.appdata.SinopeOutTempData;
import ca.tulip.sinope.core.appdata.SinopeRoomTempData;
import ca.tulip.sinope.core.internal.SinopeAnswer;
import ca.tulip.sinope.core.internal.SinopeDataAnswer;
import ca.tulip.sinope.core.internal.SinopeDataRequest;
import ca.tulip.sinope.core.internal.SinopeRequest;
import ca.tulip.sinope.util.ByteArrayConverter;
import ca.tulip.sinope.util.ByteUtil;
import ch.qos.logback.classic.Level;

/**
 * The Class SinopeProtocol.
 *
 * This is class is example to demonstrate some api commands, very low-level
 *
 *
 * 1. Update ID
 * 2. Uncomment Authentication part to get your API key.
 * 3. Update the api key
 * 4. Update device id for room temperature (to get
 *
 * The code will request some information and fall into discover mode (loops forever)
 * Push both buttons to get your device id.
 */
public class SinopeProtocol {

    public static final int API_PORT = 4550;

    final static Logger logger = LoggerFactory.getLogger(SinopeProtocol.class);
    /** The id. */
    @Parameter(names = "-gateway", converter = ByteArrayConverter.class, required = true)
    private byte[] gatewayId;

    /** The api key. */
    @Parameter(names = "-api", converter = ByteArrayConverter.class)
    private byte[] apiKey;

    @Parameter(names = "-device", converter = ByteArrayConverter.class)
    private byte[] deviceId;

    @Parameter(names = "-discover", description = "Execute discover mode to get device id")
    private boolean discover = false;

    @Parameter(names = "-demo", description = "Execute demo mode (requires api and device id)")
    private boolean demo = false;

    @Parameter(names = "-login", description = "Get Api Key")
    private boolean login = false;

    @Parameter(names = "-addr", description = "Gateway network ip or hostname", required = true)
    private String addr;

    @Parameter(names = "-debug", description = "Turn on debug")
    private boolean debug = false;

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws UnknownHostException the unknown host exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    public static void main(String[] args) throws UnknownHostException, IOException {

        SinopeProtocol main = new SinopeProtocol();
        new JCommander(main, args);
        main.run();

    }

    private void run() throws UnknownHostException, IOException {
        if (debug) {
            // Ok to reference logback in the runtime
            ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.DEBUG);
        }

        Socket clientSocket = new Socket(addr, API_PORT);

        if (this.login && !this.demo && !this.discover) {
            generateApiKey(clientSocket);
        } else if (!this.login && this.demo && !this.discover) {
            demo(clientSocket);
        } else if (!this.login && !this.demo && this.discover) {
            discover(clientSocket);
        } else {
            System.out.println("nothing to do!");
        }
        clientSocket.close();
    }

    private void demo(Socket clientSocket) throws UnknownHostException, IOException {

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println(String.format("Sinope Demo for gateway id        : %s", ByteUtil.toString(gatewayId)));
        System.out.println(String.format("Using API Key                     : %s", ByteUtil.toString(apiKey)));
        SinopeApiLoginRequest loginR = new SinopeApiLoginRequest(gatewayId, apiKey);

        SinopeApiLoginAnswer login = (SinopeApiLoginAnswer) execute(outToServer, clientSocket.getInputStream(), loginR);

        if (login.getStatus() == 0) {
            System.out.println(
                    String.format("Reading local time for device id  : %s", ByteUtil.toString(login.getDeviceId())));

            SinopeDataReadRequest req = new SinopeDataReadRequest(new byte[] { 0, 0, 3, 0 }, login.getDeviceId(),
                    new SinopeLocalTimeData());

            SinopeDataAnswer answ = (SinopeDataAnswer) execute(outToServer, clientSocket.getInputStream(), req);
            byte[] time = (answ.getAppData().getData());
            // 0 @ 23 (Normal), 128 @ 151 (DST active)
            int hour = (time[2] & 0xFF) > 23 ? (time[2] & 0xFF) - 128 : (time[2] & 0xFF);
            int minute = time[1] & 0xFF;
            int second = time[0] & 0xFF;

            System.out.println(String.format("Local time                        : %d:%d:%d", hour, minute, second));
            req = new SinopeDataReadRequest(new byte[] { 0, 0, 3, 1 }, login.getDeviceId(), new SinopeOutTempData());
            System.out.println(
                    String.format("Reading outside temp for device id: %s", ByteUtil.toString(login.getDeviceId())));
            answ = (SinopeDataAnswer) execute(outToServer, clientSocket.getInputStream(), req);
            System.out.println(String.format("Outside temp is                   : %2.2f C",
                    (((SinopeOutTempData) answ.getAppData()).getOutTemp() / 100.0)));
            System.out.println(String.format("Reading room temp for device id   : %s", ByteUtil.toString(deviceId)));
            req = new SinopeDataReadRequest(new byte[] { 0, 0, 3, 2 }, deviceId, new SinopeRoomTempData());

            answ = (SinopeDataAnswer) execute(outToServer, clientSocket.getInputStream(), req);
            System.out.println(String.format("Room temp is                      : %2.2f C",
                    (((SinopeRoomTempData) answ.getAppData()).getRoomTemp() / 100.0)));
        }

    }

    /**
     * Execute.
     *
     * @param outToServer the out to server
     * @param inputStream the input stream
     * @param command the command
     * @return the sinope answer
     * @throws UnknownHostException the unknown host exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    private static SinopeAnswer execute(DataOutputStream outToServer, InputStream inputStream,
            SinopeDataRequest command) throws UnknownHostException, IOException {

        outToServer.write(command.getPayload());

        SinopeDataAnswer answ = command.getReplyAnswer(inputStream);

        while (answ.getMore() == 0x01) {
            answ = command.getReplyAnswer(inputStream);

        }
        return answ;

    }

    /**
     * Execute.
     *
     * @param outToServer the out to server
     * @param inputStream the input stream
     * @param command the command
     * @return the sinope answer
     * @throws UnknownHostException the unknown host exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static SinopeAnswer execute(DataOutputStream outToServer, InputStream inputStream, SinopeRequest command)
            throws UnknownHostException, IOException {

        outToServer.write(command.getPayload());
        outToServer.flush();
        SinopeAnswer answ = command.getReplyAnswer(inputStream);
        return answ;

    }

    /**
     * Discover.
     *
     * @param clientSocket
     *
     * @throws UnknownHostException the unknown host exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void discover(Socket clientSocket) throws UnknownHostException, IOException {

        executePing(clientSocket);

        SinopeApiLoginAnswer login = executeLogin(clientSocket);

        if (login.getStatus() == 0) {
            logger.info("Successful login");
            while (clientSocket.isConnected()) {
                System.out.println("It is now time to push both buttons on your device!");
                System.out.println("Press crtl-c to exit!");
                SinopeDeviceReportAnswer answ = new SinopeDeviceReportAnswer(clientSocket.getInputStream());
                logger.debug("Got report answer: %s" + answ);
                System.out.println(String.format("Your device id is: %s", ByteUtil.toString(answ.getDeviceId())));
            }
        }
        clientSocket.close();

    }

    private SinopeApiLoginAnswer executeLogin(Socket clientSocket) throws UnknownHostException, IOException {
        executePing(clientSocket);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        SinopeApiLoginRequest loginR = new SinopeApiLoginRequest(gatewayId, apiKey);
        SinopeApiLoginAnswer login = (SinopeApiLoginAnswer) execute(outToServer, clientSocket.getInputStream(), loginR);
        return login;
    }

    private void executePing(Socket clientSocket) throws UnknownHostException, IOException {
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        logger.info("Ping Request");
        SinopePingAnswer ans = (SinopePingAnswer) execute(outToServer, clientSocket.getInputStream(),
                new SinopePingRequest());

        logger.debug("Got ping answer: %s" + ans);
        logger.info("Ping Received");
    }

    private void generateApiKey(Socket clientSocket) throws UnknownHostException, IOException {

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        // We start by doing a ping
        executePing(clientSocket);

        System.out.println("Getting API Key  - PRESS WEB Button");
        logger.info("AuthenticationKey Request");
        SinopeAuthenticationKeyAnswer key = (SinopeAuthenticationKeyAnswer) execute(outToServer,
                clientSocket.getInputStream(), new SinopeAuthenticationKeyRequest(gatewayId));
        logger.info("AuthenticationKey Received");
        System.out.println("Your api Key is: %s" + ByteUtil.toString(key.getApiKey()));
        clientSocket.close();
    }

}