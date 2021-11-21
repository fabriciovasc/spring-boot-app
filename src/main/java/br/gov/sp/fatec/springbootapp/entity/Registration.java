package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springbootapp.controller.View;

@Entity
@Table(name = "registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private Long id;

    @Column(name = "registration_email")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String email;

    @Column(name = "registration_password")
    @JsonView(View.RegistrationView.class)
    private String password;

    @Column(name = "registration_name")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String name;

    @Column(name = "registration_cellphone")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String cellphone;

    @Column(name = "registration_user_agent")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String userAgent;

    @Column(name = "registration_name_browser")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String nameBrowser;

    @Column(name = "registration_version_browser")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String versionBrowser;

    @Column(name = "registration_system")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String system;

    @Column(name = "registration_gpu_model")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String gpuModel;

    @Column(name = "registration_ip")
    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    private String ip;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "registrations")
    private Set<Profile> profiles;

    @JsonView({View.RegistrationAllView.class, View.RegistrationView.class, View.ProfileAllView.class})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "registration_auth",
        joinColumns = { @JoinColumn(name = "registration_id")},
        inverseJoinColumns = { @JoinColumn(name = "auth_id") }
        )
    private Set<Auth> auths;

    public Set<Auth> getAuths() {
        return auths;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return this.cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


    public String getNameBrowser() {
        return this.nameBrowser;
    }

    public void setNameBrowser(String nameBrowser) {
        this.nameBrowser = nameBrowser;
    }

    public String getVersionBrowser() {
        return this.versionBrowser;
    }

    public void setVersionBrowser(String versionBrowser) {
        this.versionBrowser = versionBrowser;
    }

    public String getSystem() {
        return this.system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getGpuModel() {
        return this.gpuModel;
    }

    public void setGpuModel(String gpuModel) {
        this.gpuModel = gpuModel;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }
}