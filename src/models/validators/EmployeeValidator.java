package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

//EmployeeValidatorクラス
public class EmployeeValidator {
    
    //バリデーション（入力チェック）実行する
    public static List<String> validate(Employee e, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
    //errorという名のList型オブジェクトを作る（文字列型）
        List<String> errors = new ArrayList<String>();

        //code_error変数はEmployeeの社員番号(models.Employee)、●codeDuplicateCheckFlag
        String code_error = validateCode(e.getCode(), codeDuplicateCheckFlag);
        //code_errorと""が違えばerrorリストにcode_error変数を追加する
        if(!code_error.equals("")) {
            errors.add(code_error);
        }
      //name_error変数はEmployeeの社員名（models.Employee）　
        String name_error = validateName(e.getName());
        //name_errorと""が違えばerrorリストにname_error変数を追加する
        if(!name_error.equals("")) {
            errors.add(name_error);
        }
        //password_error変数はEmployeeのパスワード（models.Employee）passwordCheckFlag　
        String password_error = validatePassword(e.getPassword(), passwordCheckFlag);
      //もしpassword_errorと""が違えばerrorリストにpassword_error変数を追加する
        if(!password_error.equals("")) {
            errors.add(password_error);
        }


        return errors;
    }

    // 社員番号必須入力チェック
    private static String validateCode(String code, Boolean codeDuplicateCheckFlag) {
        //もし社員番号が空の場合　"社員番号を入力してください。"
        if(code == null || code.equals("")) {
            return "社員番号を入力してください。";
        }
        // すでに登録されている社員番号との重複チェック
        if(codeDuplicateCheckFlag) {
            //EntityManagerの生成　
            EntityManager em = DBUtil.createEntityManager();
            //(長整数型)employees_count変数　＝codeとして値を指定し、models.Employeeで作成したcheckRegisteredCodeを実行、結果を単一のオブジェクトとして返す
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class).setParameter("code", code).getSingleResult();
           
            //終了処理
            em.close();
            if(employees_count > 0) {
                return "入力された社員番号の情報はすでに存在しています。";
            }
        }

        return "";
    }

    // 名前の必須入力チェック
    private static String validateName(String name) {
        //もし名前が空であれば氏名を入力てください
        if(name == null || name.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    // パスワード必須入力チェック
    private static String validatePassword(String password, Boolean passwordCheckFlag) {
        //passwordCheckFlag　パスワードが空を同時に満たす場合、パスワードを入力してください
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}