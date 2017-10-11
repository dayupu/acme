package com.manage.kernel.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by bert on 17-10-11.
 */
@Entity
@Table(name = "ad_organization")
@SequenceGenerator(name = "seq_ad_organization", sequenceName = "seq_ad_organization", allocationSize = 1)
public class AdOrganization {

    @Id
    @GeneratedValue(generator = "seq_ad_organization", strategy = GenerationType.SEQUENCE)
    private Long id;
}
