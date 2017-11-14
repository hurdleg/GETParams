package mad9132.doo.utils;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helper class for working with a remote server
 *
 * @author David Gassner
 */
public class HttpHelper {

    /**
     * Returns text from a URL on a web server (no authentication)
     *
     * @param requestPackage
     * @return
     * @throws IOException
     */
    public static String downloadUrl(RequestPackage requestPackage) throws IOException {
        return downloadUrl(requestPackage, "", "");
    }

    // TODO #5 - change method signature of downloadUrl: one parameter of type RequestPackage
    /**
     * Returns text from a URL on a web server with basic authentication
     *
     * @param requestPackage
     * @param user
     * @param password
     * @return
     * @throws IOException
     */
    public static String downloadUrl(RequestPackage requestPackage, @NonNull String user, @NonNull String password) throws IOException {

        // TODO #7 - get the endPoint (i.e. address) and encoded params from requestPackage
        String address = requestPackage.getEndpoint();
        String encodedParams = requestPackage.getEncodedParams();

        // Is the method == "GET" AND at least one param? Yes - append params as a query string
        // Format: urlAddress?queryString
        // Example: REST-API/buildings?categoryId=5
        if (requestPackage.getMethod().equals("GET") &&
                encodedParams.length() > 0) {
            address = String.format("%s?%s", address, encodedParams);
        }

        StringBuilder loginBuilder = null;
        if ( (user.isEmpty() == false) && (password.isEmpty() == false) ) {
            byte[] loginBytes = (user + ":" + password).getBytes();
            loginBuilder = new StringBuilder()
                    .append("Basic ")
                    .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));
        }

        // TODO #8 - set the HttpUrl connection's request method to requestPackage's getMethod
        InputStream is = null;
        try {
//            URL url = new URL(endpoint);
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (loginBuilder != null) {
                conn.addRequestProperty("Authorization", loginBuilder.toString());
            }
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("GET");
            conn.setRequestMethod(requestPackage.getMethod());
            conn.setDoInput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Got response code " + responseCode);
            }
            is = conn.getInputStream();
            return readStream(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * Reads an InputStream and converts it to a String.
     *
     * @param stream
     * @return
     * @throws IOException
     */
    private static String readStream(InputStream stream) throws IOException {

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        BufferedOutputStream out = null;
        try {
            int length = 0;
            out = new BufferedOutputStream(byteArray);
            while ((length = stream.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
            return byteArray.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}