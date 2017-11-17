package com.demo.domain;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity // 代表被映射实体类
@Table(name = "contact") // 定义实体映射到数据库中的表名
// 命名查询,相当于HQL查询
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "select c from Contact c"),
    @NamedQuery(name = "Contact.findById",
            query = "select distinct c from Contact c left join fetch c.contactTelDetails t left join fetch c.hobbies h where c.id = :id"),
    @NamedQuery(name = "Contact.findAllWithDetail",
            query = "select distinct c from Contact c left join fetch c.contactTelDetails t left join fetch c.hobbies h")
})
public class Contact implements Serializable {
    private Long id;
    private int version;
    private String firstName;
    private String lastName;
    private DateTime birthDate;
    private String description;
    private byte[] photo;
    private Set<ContactTelDetail> contactTelDetails = new HashSet<ContactTelDetail>();
    private Set<Hobby> hobbies = new HashSet<Hobby>();

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 告诉Hibernate如何生成id,IDENTITY策略指由数据库生成
    @Column(name = "ID") // 列名
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version // 指定Hibernate使用乐观锁机制
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    // 在数据绑定过程中启用JSR-349校验,应用于Controller类中方法的带有@Valid的参数
    // ContactController:
    //     public String update(@Valid Contact contact, ...);
    // message: 数据绑定时验证失败回显到前端页面的消息
    @NotEmpty(message = "请输入名称")
    @Size(min = 3, max = 60, message = "名称的长度是 {min} 至 {max}")
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty(message = "请输入姓氏")
    @Size(min = 1, max = 40, message = "姓氏的长度是 {min} 至 {max}")
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // 将Joda-time库的DateTime类型的属性持久化到数据库中的TIMESTAMP列
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // DateTime属性的日期格式 yyyy-MM-dd
    @Column(name = "BIRTH_DATE")
    public DateTime getBirthDate() {
        return birthDate;
    }

    // 如使用java.util.Date类型作为属性
    // 在其get方法上添加@Temporal(TemporalType.DATE)且指定@Column即可

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic(fetch = FetchType.LAZY) // 延迟加载
    @Lob // 告诉JPA提供商这是一个lob列
    @Column(name = "PHOTO")
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // 指定与ContactTelDetail类一对多的关系
    // mappedBy: 指定"多"方提供关联的属性
    // cascade: 更新(增删改)操作会级联到子表
    // orphanRemoval: 孤儿删除, "一"方的"多"方属性更新后,不在集合中的实体实例也将从数据库中删除
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<ContactTelDetail> getContactTelDetails() {
        return contactTelDetails;
    }

    public void setContactTelDetails(Set<ContactTelDetail> contactTelDetails) {
        this.contactTelDetails = contactTelDetails;
    }

    public void addContactTelDetail(ContactTelDetail contactTelDetail) {
        contactTelDetail.setContact(this);
        getContactTelDetails().add(contactTelDetail);
    }

    public void removeContactTelDetail(ContactTelDetail contactTelDetail) {
        getContactTelDetails().remove(contactTelDetail);
    }

    @ManyToMany // 多对多
    @JoinTable(name = "contact_hobby_detail", // 指定Hibernate应该查找的链接表
            joinColumns = @JoinColumn(name = "CONTACT_ID"), // 定义关联CONTACT表的外键
            inverseJoinColumns = @JoinColumn(name = "HOBBY_ID")) // 定义关联另一方的外键
    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Transient
    public String getBirthDateString() {
        String birthDateString = "";
        if (birthDate != null) {
            birthDateString = org.joda.time.format.DateTimeFormat
                    .forPattern("yyyy-MM-dd").print(birthDate);
        }
        return birthDateString;
    }

    @Override
    public String toString() {
        return "Contact - Id: " + id + ", First name: " + firstName
                + ", Last name: " + lastName + ", Birthday: " + birthDate
                + ", Description: " + description;
    }
}
