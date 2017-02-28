package Audit_Trail;

import java.util.Date;

/**
 * Created by ultimaq on 17/02/26.
 */
public class AuditEntry {

    private String RD;
    private Date birth;
    private String msg;
    public AuditEntry(String RD, Date d, String msg){
       this.RD = RD;
       this.birth = d;
       this.msg = msg;
    }

    public AuditEntry(){
        this.msg = "";
        this.birth = null;
        this.RD = "";
    }

    @Override
    public String toString(){
        return "AuditEntry Date: " + birth + "\n\tMessage: " +  msg;
    }

    public String getRD(){
       return this.RD;
    }

    public void setRD(String RD){
        this.RD = RD;
    }

    public Date getBirth(){
        return birth;
    }

    public void setBirth(Date birth){
        this.birth = birth;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}
