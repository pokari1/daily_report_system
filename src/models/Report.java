package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
//reportsクラス
//Entityにマッピングされるテーブル名を指定
@Table(name = "reports")
@NamedQueries({
    @NamedQuery(
        name = "getAllReports",
                //要素にrという名前を付ける　employeeテーブルから全情報を取得し、　e要素のidの降順（高い順）に並べ替える
        query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
    ),
    @NamedQuery(
        name = "getReportsCount",
                //要素にrという名前を付け,Reportテーブル全てから取得しデータ数を取得
        query = "SELECT COUNT(r) FROM Report AS r"
    ),
    @NamedQuery(
        name = "getMyAllReports",
                //指定された社員番号がすでにデータベースに存在しているかを調べる
                //要素にeという名前を付け,employeeテーブルとコードが同じ場合データ数を取得
        query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"
    ),
    @NamedQuery(
        name = "getMyReportsCount",
                //delete_flagが0かつコードが一致、パスワードが一致した場合、データを取得
                //従業員がログインするときに社員番号とパスワードが正しいかをチェック
        query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"
    )
})
@Entity
public class Report{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //●多対1　所有者側のエンティティクラスから被所有者側のエンティティクラスへの関連を指定するアノテーション
    @ManyToOne
    //エンティティクラス間の関連づけをする場合に，結合表のための外部キーカラムまたは外部キーカラムによって参照された
    //結合先テーブルのカラム名を指定するアノテーション
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    //年月日で管理
    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    //テキストエリアの指定　改行もデータベースに保存
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    // getter/setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}