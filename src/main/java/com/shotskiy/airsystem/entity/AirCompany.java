package com.shotskiy.airsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "air_company")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AirCompany{

    public AirCompany(String name, String companyType, Date foundedAt) {
        this.name = name;
        this.companyType = companyType;
        this.foundedAt = foundedAt;
    }

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    private String name;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "founded_at")
    private Date foundedAt;

    @OneToMany(mappedBy = "airCompany", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("airCompany")
    private List<Airplane> airplanes;


    @OneToMany(mappedBy = "airCompany", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("airCompany")
    private List<Flight> flights;

    @Override
    public String toString() {
        return "AirCompany{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", companyType='" + companyType + '\'' +
                ", foundedAt=" + foundedAt +
                '}';
    }


}
