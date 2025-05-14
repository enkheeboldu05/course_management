package src.model;

public class Student{
    private int id;
    private String name;
    private String email;
    private String major;

    public Student(String name, String email, String major){
        this.name = name;
        this.email = email;
        this.major = major;
    }
    
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public String getName(){ return name; }
    public void setName( String name ){ this.name = name; }
    
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getMajor(){ return major; }
    public void setMajor(){ this.major = major; }
}