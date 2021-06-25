package com.pixelprodukt.healthapprestbackend.model.user

import com.pixelprodukt.healthapprestbackend.model.audit.DateAudit
import com.pixelprodukt.healthapprestbackend.model.bloodpressure.BloodPressure
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(
    name = "users", uniqueConstraints = [
        UniqueConstraint(columnNames = arrayOf("email")),
        UniqueConstraint(columnNames = arrayOf("name"))
    ]
)
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Size(max = 200) var name: String,
    @NotNull @NotBlank @Email @Size(max = 200) var email: String,
    @NotNull @NotBlank @Size(max = 200) var password: String,
) : DateAudit()