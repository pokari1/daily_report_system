package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Customer;


//バリデーション（入力チェック）実行する
public class CustomerValidator {
    public static List<String> validate(Customer c) {
        List<String> errors = new ArrayList<String>();

        String name_error = _validateName(c.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }


        return errors;
    }


    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "会社名を入力してください。";
            }

        return "";
    }


}