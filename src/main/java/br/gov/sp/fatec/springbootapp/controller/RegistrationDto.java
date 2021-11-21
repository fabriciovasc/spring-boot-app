package br.gov.sp.fatec.springbootapp.controller;

public class RegistrationDto {

    public String email;

    public String password;
    
    public String name;

    public String cellphone;

    public String uniqueHash;

    public String nameBrowser;

    public String versionBrowser;

    public String system;

    public String gpuModel;

    public String userAgent;

    public String ip;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
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

    public String getUniqueHash() {
        return uniqueHash;
    }

    public void setUniqueHash(String uniqueHash) {
        this.uniqueHash = uniqueHash;
    }   
    
}
