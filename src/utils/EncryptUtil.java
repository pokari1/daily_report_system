package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class EncryptUtil {
    public static String getPasswordEncrypt(String plain_p, String pepper) {
        String ret = "";

        //もしplain_pが空でなければ引数で受け取った文字列にペッパー文字列を連結させたものを SHA256 でハッシュ化します。
        //引数の文字列が何もなければ、空の文字列を返す。
        if(plain_p != null && !plain_p.equals("")) {
            byte[] bytes;
            String password = plain_p + pepper;
            try {
                //SHA-256をつかってpasswordをハッシュ化
                bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
                //16新数文字列へ変換
                ret = DatatypeConverter.printHexBinary(bytes);
                //NoSuchAlgorithmExceptionの例外があった場合なにもしない
            } catch(NoSuchAlgorithmException ex) {}
        }

        return ret;
    }
}