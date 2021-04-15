package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
//employeeクラス
//Entityにマッピングされるテーブル名を指定
@Table(name = "employees")
//Queryをカンマで区切り指定
@NamedQueries({
    //主キー以外の項目などで検索し、複数件の結果を取得したい場合に定義する
    @NamedQuery(
        name = "getAllEmployees",
        //要素にeという名前を付ける　employeeテーブルから全情報を取得し、　e要素のidの降順（高い順）に並べ替える
        query = "SELECT e FROM Employee AS e ORDER BY e.id DESC"
    ),
    @NamedQuery(
        name = "getEmployeesCount",
        //要素にeという名前を付け,employeeテーブル全てから取得しデータ数を取得
        query = "SELECT COUNT(e) FROM Employee AS e"
    ),
    @NamedQuery(
        name = "checkRegisteredCode",
        //指定された社員番号がすでにデータベースに存在しているかを調べる
        //要素にeという名前を付け,employeeテーブルとコードが同じ場合データ数を取得
        query = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :code"
    ),
    @NamedQuery(
        name = "checkLoginCodeAndPassword",
        //delete_flagが0かつコードが一致、パスワードが一致した場合、データを取得
        //従業員がログインするときに社員番号とパスワードが正しいかをチェック
        query = "SELECT e FROM Employee AS e WHERE e.delete_flag = 0 AND e.code = :code AND e.password = :pass"
    )
})

//クラスがEntityクラスであることを指定
@Entity
public class Employee {
    //主キーのフィールドに指定
    @Id
    //カラム名を指定
    @Column(name = "id")
    //主キー値を自動採番すること
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //カラム名をcodeに指定（文字列）、nullを許容しない、既に存在している番号は登録できない(一意制約)。
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    //カラム名をnameに指定、nullを許容しない、
    @Column(name = "name", nullable = false)
    private String name;

    //カラム名をpasswordに指定（文字列）、nullを許容しない、入力できる文字情報が最大64文字(SHA256ハッシュ関数利用のため)
    @Column(name = "password", length = 64, nullable = false)
    private String password;

    //管理者権限あるかどうか　数値型（一般：0、管理者：1）
    @Column(name = "admin_flag", nullable = false)
    private Integer admin_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    //削除された従業員かどうか　数値型（現役：0、削除済み：1）
    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;


    // getter/setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }
}