package com.pixelprodukt.healthapprestbackend.model.allergy

import com.pixelprodukt.healthapprestbackend.model.audit.DateAudit
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "allergies")
class Allergy(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Column(name = "user_id") var userId: Long,
    @NotNull @Size(max = 100) var description: String,
    @Size(max = 600) var reaction: String
) : DateAudit()