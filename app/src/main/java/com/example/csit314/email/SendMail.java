package com.example.csit314.email;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGSaver;

public class SendMail extends AsyncTask<Void, Void, Void> {
    public static final String EMAIL = "dev314sim@gmail.com"; //
    public static final String PASSWORD = "1zxcvbnm!";
    private Context context;
    private Session session;
    private String email, subject, message;
    private String url;
    public SendMail(Context context, String email, String subject, String message,String url) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.url = url;
    }
    public SendMail(Context context, String email, String subject, String message) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.url = "";
    }
    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(EMAIL));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject(subject);
            if(url != "")
            {
                File file = new File(url);

                //As inline
                Multipart multipart = new MimeMultipart();

                // create bodypart with image and set content-id
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(new File(url));
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("image.png");
                messageBodyPart.setDisposition(MimeBodyPart.INLINE);
                messageBodyPart.setHeader("Content-ID", "<vogue>");
                multipart.addBodyPart(messageBodyPart);

                // create bodypart with html content and reference to the content-id
                messageBodyPart = new MimeBodyPart();
                String htmlText = message + "<br><br><img src=\"cid:vogue\">";
                messageBodyPart.setContent(htmlText, "text/html");
                multipart.addBodyPart(messageBodyPart);

                // add multipart to message
                mimeMessage.setContent(multipart);
                // Create the message part
            }

            Transport.send(mimeMessage);
            Log.i("SendEmail","Sending Email");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }


}
