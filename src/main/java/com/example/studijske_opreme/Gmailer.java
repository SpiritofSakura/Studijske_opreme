//package com.example.studijske_opreme;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//public class Gmailer {
//
//    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
//            throws IOException {
//        // Load client secrets.
//        InputStream in = Gmailer.class.getResourceAsStream("/...json");
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets =
//                GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//        //returns an authorized Credential object.
//        return credential;
//    }
//    public void sendMail(String zadeva, String sporocilo)
//    {
//        //poslje email nam
//    }
//
//    public static void main (String[]args ){
//        new Gmailer().sendMail("zadeva","sporocilo");
//    }
//}
